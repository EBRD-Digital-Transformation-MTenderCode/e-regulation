package com.procurement.regulation.model.dto.templates

import com.fasterxml.jackson.annotation.JsonCreator
import java.util.*

data class TemplateRule @JsonCreator constructor(

        val templateIds: LinkedList<String>
)