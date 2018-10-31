package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Period(

        val startDate: LocalDateTime?,

        val endDate: LocalDateTime?
)
