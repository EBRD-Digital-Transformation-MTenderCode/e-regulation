package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Classification @JsonCreator constructor(

        val scheme: String,

        val id: String,

        val description: String,

        val uri: String?
)
