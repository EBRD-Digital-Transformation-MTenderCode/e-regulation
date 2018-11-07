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
class TermsService(private val templateService: TemplateService,
                   private val termsDao: TermsDao) {

    fun getTerms(cm: CommandMessage): ResponseDto {
        val country = cm.context.country ?: throw ErrorException(ErrorType.CONTEXT)
        val pmd = cm.context.pmd ?: throw ErrorException(ErrorType.CONTEXT)
        val language = cm.context.language ?: throw ErrorException(ErrorType.CONTEXT)
        val mainProcurementCategory = cm.context.mainProcurementCategory ?: throw ErrorException(ErrorType.CONTEXT)
        val dto = toObject(GetTermsRq::class.java, cm.data)

        val staticTemplates = templateService.getStaticMetrics(country, pmd, language, mainProcurementCategory)
        val dynamicTemplates = templateService.getDynamicMetrics(country, pmd, language, mainProcurementCategory)
        val contractTerms = mutableSetOf<ContractTerm>()
        val entities = mutableListOf<TermsEntity>()
        for (contract in dto.contracts) {
            val award = dto.awards.asSequence().firstOrNull { it.id == contract.awardId }
                    ?: throw ErrorException(ErrorType.AWARD_NOT_FOUND)
            val agreedMetrics = LinkedList<AgreedMetric>()
            agreedMetrics.addAll(staticTemplates)
            val items = award.items
            if (items != null && items.isNotEmpty()) {
                for (dynamicTemplate in dynamicTemplates) {
                    for (item in items) {
                        val id = dynamicTemplate.id + "-" + award.id + "-" + item.id
                        val title =  item.description + ": subject specification"
                        agreedMetrics.add(dynamicTemplate.copy(id = id, title = title))
                    }
                }
            }
            val contractTerm = ContractTerm(id = contract.id, agreedMetrics = agreedMetrics)
            entities.add(TermsEntity(contract.id, toJson(contractTerm)))
            contractTerms.add(contractTerm)
        }
        termsDao.saveAll(entities)
        return ResponseDto(data = GetTermsRs(contractTerms))
    }

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
                        val measure = agreedMetricRq.observations.asSequence().firstOrNull { it.id == observation.id }
                        observation.unit?.measure = measure
                    }
                }
            }
        }
        entity.jsonData = toJson(contractTerm)
        termsDao.save(entity)
        return ResponseDto(data = UpdateTermsRs(agreedMetricsDb))
    }
}
