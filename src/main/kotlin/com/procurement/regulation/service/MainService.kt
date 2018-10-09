package com.procurement.regulation.service

import com.procurement.regulation.dao.MainDao
import com.procurement.regulation.model.dto.bpe.CommandMessage
import com.procurement.regulation.model.dto.bpe.ResponseDto
import org.springframework.stereotype.Service

interface MainService {

    fun getTerms(cm: CommandMessage): ResponseDto
}

@Service
class MainServiceImpl(private val mainDao: MainDao,
                      private val generationService: GenerationService) : MainService {

    override fun getTerms(cm: CommandMessage): ResponseDto {
//        mainDao.testsave()
        return ResponseDto(data = "{}")
    }
}
