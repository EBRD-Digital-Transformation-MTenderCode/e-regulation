package com.procurement.regulation.controller

import com.procurement.regulation.exception.EnumException
import com.procurement.regulation.exception.ErrorException
import com.procurement.regulation.model.dto.bpe.*
import com.procurement.regulation.service.CreateTermsService
import com.procurement.regulation.service.UpdateTermsService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@Validated
@RestController
@RequestMapping("/command")
class CommandController(private val createTermsService: CreateTermsService,
                        private val updateTermsService: UpdateTermsService) {

    @PostMapping
    fun command(@RequestBody commandMessage: CommandMessage): ResponseEntity<ResponseDto> {
        return ResponseEntity(execute(commandMessage), HttpStatus.OK)
    }

    fun execute(cm: CommandMessage): ResponseDto {
        return when (cm.command) {
            CommandType.GET_TERMS -> createTermsService.getTerms(cm)
            CommandType.UPDATE_TERMS -> updateTermsService.updateTerms(cm)
        }
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @ExceptionHandler(Exception::class)
    fun exception(ex: Exception): ResponseDto {
        return when (ex) {
            is ErrorException -> getErrorExceptionResponseDto(ex)
            is EnumException -> getEnumExceptionResponseDto(ex)
            else -> getExceptionResponseDto(ex)
        }
    }
}



