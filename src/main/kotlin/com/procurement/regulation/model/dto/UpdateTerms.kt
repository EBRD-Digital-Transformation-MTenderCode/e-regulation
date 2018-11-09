package com.procurement.regulation.model.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.procurement.regulation.model.dto.bpe.templates.AgreedMetric
import java.util.*

data class UpdateTermsRq @JsonCreator constructor(

        val agreedMetrics: LinkedList<AgreedMetric>
)

data class UpdateTermsRs @JsonCreator constructor(

        val agreedMetrics: LinkedList<AgreedMetric>
)