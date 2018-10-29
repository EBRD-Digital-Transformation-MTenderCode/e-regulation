package com.procurement.regulation.service

import com.procurement.regulation.dao.TermsDao
import com.procurement.regulation.exception.ErrorException
import com.procurement.regulation.exception.ErrorType
import com.procurement.regulation.model.dto.bpe.CommandMessage
import com.procurement.regulation.model.dto.bpe.ResponseDto
import com.procurement.regulation.model.dto.bpe.templates.ContractTerm
import com.procurement.regulation.model.dto.request.GetTermsRq
import com.procurement.regulation.model.dto.request.GetTermsRs
import com.procurement.regulation.model.entity.TermsEntity
import com.procurement.regulation.utils.toJson
import com.procurement.regulation.utils.toObject
import org.springframework.stereotype.Service

@Service
class TermsService(private val templateService: TemplateService,
                   private val termsDao: TermsDao) {

    fun getTerms(cm: CommandMessage): ResponseDto {
        val country = cm.context.country ?: throw ErrorException(ErrorType.CONTEXT)
        val pmd = cm.context.pmd ?: throw ErrorException(ErrorType.CONTEXT)
        val language = cm.context.language ?: throw ErrorException(ErrorType.CONTEXT)
        val mainProcurementCategory = cm.context.mainProcurementCategory ?: throw ErrorException(ErrorType.CONTEXT)
        val dto = toObject(GetTermsRq::class.java, cm.data)

        val staticMetrics = templateService.getStaticMetrics(country, pmd, language, mainProcurementCategory)
        val dynamicMetrics = templateService.getDynamicMetrics(country, pmd, language, mainProcurementCategory)
        val contractTerms = mutableSetOf<ContractTerm>()
        val entities = mutableListOf<TermsEntity>()
        for (contract in dto.contracts) {
            val award = dto.awards.asSequence().firstOrNull { it.id == contract.awardId }
                    ?: throw ErrorException(ErrorType.AWARD_NOT_FOUND)
            val items = award.items
            if (items != null && items.isNotEmpty()) {
                for (dynamicMetric in dynamicMetrics) {
                    for (item in items)
                        dynamicMetric.id = dynamicMetric.id + award.id + item.id
                }
            }

            contractTerms.add(ContractTerm(id = contract.id, agreedMetrics = staticMetrics + dynamicMetrics))
            entities.add(TermsEntity(contract.id, toJson(contractTerms)))
        }
        termsDao.saveAll(entities)
        return ResponseDto(data = GetTermsRs(contractTerms))
    }
}
