package com.procurement.regulation.model.dto.bpe.templates

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude


data class ContractTerms @JsonCreator constructor(

        val id: String,

        val agreedMetrics: Set<AgreedMetric>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AgreedMetric @JsonCreator constructor(

        val id: String,

        val title: String,

        val description: String,

        val observations: Set<Observation>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Observation @JsonCreator constructor(

        val id: String,

        val notes: String,

        val unit: ObservationUnit?
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class ObservationUnit @JsonCreator constructor(

        val id: String,

        val name: String,

        val scheme: String,

        val measure: Any?
)