package com.procurement.regulation.service

import com.procurement.regulation.dao.DataDao
import com.procurement.regulation.exception.ErrorException
import com.procurement.regulation.exception.ErrorType
import com.procurement.regulation.model.dto.bpe.CommandMessage
import com.procurement.regulation.model.dto.bpe.ResponseDto
import com.procurement.regulation.model.dto.bpe.templates.ContractTerms
import org.springframework.stereotype.Service

@Service
class MainService(private val templateService: TemplateService,
                      private val dataDao: DataDao,
                      private val generationService: GenerationService) {

    fun getTerms(cm: CommandMessage): ResponseDto {
        val country = cm.context.country ?: throw ErrorException(ErrorType.CONTEXT)
        val pmd = cm.context.pmd ?: throw ErrorException(ErrorType.CONTEXT)
        val language = cm.context.language ?: throw ErrorException(ErrorType.CONTEXT)
//        val dto = toObject(GetTermsRq::class.java, cm.data)
        val staticMetrics = templateService.getStaticMetrics(country, pmd, language, "services")
        val dynamicMetrics = templateService.getDynamicMetrics(country, pmd, language, "services")
        return ResponseDto(data = ContractTerms(id = "", agreedMetrics = staticMetrics + dynamicMetrics))
    }
}
