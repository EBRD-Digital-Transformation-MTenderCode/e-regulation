package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import org.w3c.dom.DocumentType
import java.util.*

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Document @JsonCreator constructor(

        val id: String,

        val documentType: DocumentType,

        val title: String?,

        val description: String?,

        val relatedLots: HashSet<String>?
)