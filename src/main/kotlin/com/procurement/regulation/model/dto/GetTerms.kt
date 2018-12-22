package com.procurement.regulation.model.dto

import com.fasterxml.jackson.annotation.JsonCreator
import com.procurement.regulation.model.dto.bpe.templates.ContractTerm
import com.procurement.regulation.model.dto.ocds.ContractedAward
import com.procurement.regulation.model.dto.ocds.Contract

data class GetTermsRq @JsonCreator constructor(

        val contractedAward: ContractedAward,

        val contract: Contract
)

data class GetTermsRs @JsonCreator constructor(

        val contractTerm: ContractTerm
)