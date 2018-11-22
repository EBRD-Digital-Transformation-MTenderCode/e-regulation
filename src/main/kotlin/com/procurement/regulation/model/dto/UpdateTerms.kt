package com.procurement.regulation.model.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonInclude
import com.procurement.regulation.model.dto.bpe.templates.AgreedMetric
import java.util.*

data class UpdateTermsRq @JsonCreator constructor(

        val agreedMetrics: LinkedList<AgreedMetricUpdate>
)

data class UpdateTermsRs @JsonCreator constructor(

        val agreedMetrics: LinkedList<AgreedMetric>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class AgreedMetricUpdate @JsonCreator constructor(

        var id: String,

        var observations: LinkedList<Observation>
)

@JsonInclude(JsonInclude.Include.NON_NULL)
data class Observation @JsonCreator constructor(

        val id: String,

        var measure: Any
)
