package com.procurement.regulation.service

import com.procurement.regulation.dao.TermsDao
import com.procurement.regulation.exception.ErrorException
import com.procurement.regulation.exception.ErrorType
import com.procurement.regulation.model.dto.GetTermsRq
import com.procurement.regulation.model.dto.GetTermsRs
import com.procurement.regulation.model.dto.UpdateTermsRq
import com.procurement.regulation.model.dto.UpdateTermsRs
import com.procurement.regulation.model.dto.bpe.CommandMessage
import com.procurement.regulation.model.dto.bpe.ResponseDto
import com.procurement.regulation.model.dto.bpe.templates.AgreedMetric
import com.procurement.regulation.model.dto.bpe.templates.ContractTerm
import com.procurement.regulation.model.entity.TermsEntity
import com.procurement.regulation.utils.toJson
import com.procurement.regulation.utils.toObject
import org.springframework.stereotype.Service
import java.util.*

@Service
class UpdateTermsService(private val templateService: TemplateService,
                         private val termsDao: TermsDao) {

      fun updateTerms(cm: CommandMessage): ResponseDto {
        val ocid = cm.context.ocid ?: throw ErrorException(ErrorType.CONTEXT)
        val dto = toObject(UpdateTermsRq::class.java, cm.data)

        val entity = termsDao.getById(ocid) ?: throw ErrorException(ErrorType.TERM_NOT_FOUND)
        val contractTerm = toObject(ContractTerm::class.java, entity.jsonData)
        val agreedMetricsRq = dto.agreedMetrics
        val agreedMetricsDb = contractTerm.agreedMetrics
        val agreedMetricsRqIds = agreedMetricsRq.asSequence().map { it.id }.toSet()
        val agreedMetricsDbIds = agreedMetricsDb.asSequence().map { it.id }.toSet()
        if (!agreedMetricsDbIds.containsAll(agreedMetricsRqIds)) throw ErrorException(ErrorType.INVALID_METRIC_ID)
        for (agreedMetricRq in agreedMetricsRq) {
            for (agreedMetricDb in agreedMetricsDb) {
                if (agreedMetricDb.id == agreedMetricRq.id) {
                    for (observation in agreedMetricDb.observations) {
                        val observationRq = agreedMetricRq.observations.asSequence().firstOrNull { it.id == observation.id }
                        observation.unit?.measure = observationRq?.measure
                    }
                }
            }
        }
        entity.jsonData = toJson(contractTerm)
        termsDao.save(entity)
        return ResponseDto(data = UpdateTermsRs(agreedMetricsDb))
    }
}
