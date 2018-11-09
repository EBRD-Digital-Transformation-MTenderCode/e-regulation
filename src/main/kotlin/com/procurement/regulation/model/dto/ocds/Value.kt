package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.procurement.regulation.model.dto.databinding.MoneyDeserializer
import java.math.BigDecimal

data class Value(

        @field:JsonDeserialize(using = MoneyDeserializer::class)
        val amount: BigDecimal?,

        val currency: String?
)