package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class OrganizationReference @JsonCreator constructor(

        val id: String,

        val name: String
)