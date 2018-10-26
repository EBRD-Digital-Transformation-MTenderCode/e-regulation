package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonCreator

data class Details @JsonCreator constructor(

        val scale: String
)
