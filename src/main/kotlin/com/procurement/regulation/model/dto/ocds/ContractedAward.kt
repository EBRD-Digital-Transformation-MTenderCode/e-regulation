package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import java.time.LocalDateTime

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ContractedAward @JsonCreator constructor(

        val id: String,

        var items: List<Item>?
)