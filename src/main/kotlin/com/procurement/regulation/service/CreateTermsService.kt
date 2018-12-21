package com.procurement.regulation.service

import com.procurement.regulation.dao.TermsDao
import com.procurement.regulation.exception.ErrorException
import com.procurement.regulation.exception.ErrorType
import com.procurement.regulation.model.dto.GetTermsRq
import com.procurement.regulation.model.dto.GetTermsRs
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
class CreateTermsService(private val templateService: TemplateService,
                         private val termsDao: TermsDao) {

    fun getTerms(cm: CommandMessage): ResponseDto {
        val country = cm.context.country ?: throw ErrorException(ErrorType.CONTEXT)
        val pmd = cm.context.pmd ?: throw ErrorException(ErrorType.CONTEXT)
        val language = cm.context.language ?: throw ErrorException(ErrorType.CONTEXT)
        val mainProcurementCategory = cm.context.mainProcurementCategory ?: throw ErrorException(ErrorType.CONTEXT)
        val dto = toObject(GetTermsRq::class.java, cm.data)
        val contract = dto.contract
        val award = dto.contractedAward
        val staticTemplates = templateService.getStaticMetrics(country, pmd, language, mainProcurementCategory)
        val dynamicTemplates = templateService.getDynamicMetrics(country, pmd, language, mainProcurementCategory)
        val contractTerms = mutableSetOf<ContractTerm>()
        val agreedMetrics = LinkedList<AgreedMetric>()
        agreedMetrics.addAll(staticTemplates)
        val items = award.items
        if (items != null && items.isNotEmpty()) {
            for (dynamicTemplate in dynamicTemplates) {
                for (item in items) {
                    val id = dynamicTemplate.id + "-" + award.id + "-" + item.id
                    val title = dynamicTemplate.title + item.description
                    agreedMetrics.add(dynamicTemplate.copy(id = id, title = title))
                }
            }
        }
        val contractTerm = ContractTerm(id = contract.id, agreedMetrics = agreedMetrics)
        termsDao.save(TermsEntity(contract.id, toJson(contractTerm)))
        contractTerms.add(contractTerm)
        return ResponseDto(data = GetTermsRs(contractTerms))
    }
}
