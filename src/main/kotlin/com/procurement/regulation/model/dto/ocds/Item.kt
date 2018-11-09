package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Item @JsonCreator constructor(

        var id: String?,

        var description: String?,

        val classification: Classification?,

        val additionalClassifications: HashSet<Classification>?,

        val quantity: BigDecimal?,

        val unit: Unit?,

        var relatedLot: String?
)