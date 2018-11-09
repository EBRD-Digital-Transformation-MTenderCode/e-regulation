package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonCreator

data class Lot @JsonCreator constructor(

        val id: String?
)