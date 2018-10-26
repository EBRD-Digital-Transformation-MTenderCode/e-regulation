package com.procurement.regulation.model.dto.templates

import com.fasterxml.jackson.annotation.JsonCreator

data class TemplateRule @JsonCreator constructor(

        val templateIds: Set<String>
)