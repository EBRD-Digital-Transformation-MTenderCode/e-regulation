package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Contract @JsonCreator constructor(

        val id: String,

        val awardId: String?,

        val status: String?,

        val statusDetails: String?
)