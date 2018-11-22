package com.procurement.regulation.service

import com.procurement.regulation.dao.TermsDao
import com.procurement.regulation.exception.ErrorException
import com.procurement.regulation.exception.ErrorType
import com.procurement.regulation.model.dto.UpdateTermsRq
import com.procurement.regulation.model.dto.UpdateTermsRs
import com.procurement.regulation.model.dto.bpe.CommandMessage
import com.procurement.regulation.model.dto.bpe.ResponseDto
import com.procurement.regulation.model.dto.bpe.templates.ContractTerm
import com.procurement.regulation.utils.toJson
import com.procurement.regulation.utils.toObject
import org.springframework.stereotype.Service

@Service
class UpdateTermsService(private val termsDao: TermsDao) {

    fun updateTerms(cm: CommandMessage): ResponseDto {
        val ocid = cm.context.ocid ?: throw ErrorException(ErrorType.CONTEXT)
        val dto = toObject(UpdateTermsRq::class.java, cm.data)

        val entity = termsDao.getById(ocid) ?: throw ErrorException(ErrorType.TERM_NOT_FOUND)
        val contractTerm = toObject(ContractTerm::class.java, entity.jsonData)
        val agreedMetricsRq = dto.agreedMetrics
        val agreedMetricsDb = contractTerm.agreedMetrics
        val agreedMetricsRqIds = agreedMetricsRq.asSequence().map { it.id }.toSet()
        val agreedMetricsDbIds = agreedMetricsDb.asSequence().map { it.id }.toSet()
        if (!agreedMetricsDbIds.containsAll(agreedMetricsRqIds))
            throw ErrorException(ErrorType.INVALID_METRIC_ID, (agreedMetricsRqIds - agreedMetricsDbIds).toString())
        val observationRqIds = agreedMetricsRq.asSequence().flatMap { it.observations.asSequence() }.map { it.id }.toSet()
        val observationDbIds = agreedMetricsDb.asSequence().flatMap { it.observations.asSequence() }.map { it.id }.toSet()
        if (!observationDbIds.containsAll(observationRqIds))
            throw ErrorException(ErrorType.INVALID_OBSERVATION_ID, (observationRqIds - observationDbIds).toString())
        agreedMetricsRq.forEach { agreedMetricRq ->
            agreedMetricsDb.forEach { agreedMetricDb ->
                if (agreedMetricDb.id == agreedMetricRq.id)
                    agreedMetricDb.observations.forEach { observationDb ->
                        agreedMetricRq.observations.forEach { observationRq ->
                            if (observationRq.id == observationDb.id) observationDb.measure = observationRq.measure
                        }
                    }
            }
        }
        entity.jsonData = toJson(contractTerm)
        termsDao.save(entity)
        return ResponseDto(data = UpdateTermsRs(agreedMetricsDb))
    }
}
