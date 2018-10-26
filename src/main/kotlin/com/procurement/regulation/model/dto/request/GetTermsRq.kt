package com.procurement.regulation.model.dto.request

import com.fasterxml.jackson.annotation.JsonCreator
import com.procurement.regulation.model.dto.ocds.Award
import com.procurement.regulation.model.dto.ocds.Contract

data class GetTermsRq @JsonCreator constructor(

        val awards: Set<Award>,

        val contracts: Set<Contract>,

        val tender: CreateContractTender
)

data class CreateContractTender @JsonCreator constructor(

        val mainProcurementCategory: String
)
