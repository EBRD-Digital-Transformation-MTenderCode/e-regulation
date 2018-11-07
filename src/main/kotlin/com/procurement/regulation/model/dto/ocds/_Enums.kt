package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonValue

enum class MainProcurementCategory(@JsonValue val value: String) {
    GOODS("goods"),
    WORKS("works"),
    SERVICES("services");

    override fun toString(): String {
        return this.value
    }
}