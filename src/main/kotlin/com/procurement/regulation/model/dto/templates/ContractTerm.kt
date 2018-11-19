package com.procurement.regulation.model.dto.bpe.templates

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import java.util.*


data class ContractTerm @JsonCreator constructor(

        val id: String,

        val agreedMetrics: LinkedList<AgreedMetric>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AgreedMetric @JsonCreator constructor(

        var id: String,

        val title: String,

        val description: String,

        var observations: LinkedList<Observation>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Observation @JsonCreator constructor(

        val id: String,

        val notes: String,

        var measure: Any?,

        val unit: ObservationUnit?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ObservationUnit @JsonCreator constructor(

        val id: String,

        val name: String,

        val scheme: String
)