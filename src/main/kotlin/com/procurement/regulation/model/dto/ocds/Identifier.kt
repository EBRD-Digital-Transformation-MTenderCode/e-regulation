package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonCreator

data class Identifier @JsonCreator constructor(

        val id: String?,

        val scheme: String?,

        val legalName: String?,

        val uri: String?
)