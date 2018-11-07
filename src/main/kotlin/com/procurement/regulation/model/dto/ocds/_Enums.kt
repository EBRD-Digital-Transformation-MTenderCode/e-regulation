package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonValue
import com.procurement.regulation.exception.EnumException

enum class MainProcurementCategory(@JsonValue val value: String) {
    GOODS("goods"),
    WORKS("works"),
    SERVICES("services");

    override fun toString(): String {
        return this.value
    }

    companion object {
        private val CONSTANTS = HashMap<String, MainProcurementCategory>()

        init {
            MainProcurementCategory.values().forEach { CONSTANTS[it.value] = it }
        }

        fun fromValue(v: String): MainProcurementCategory {
            return CONSTANTS[v] ?: throw EnumException(MainProcurementCategory::class.java.name, v, values().toString())
        }
    }
}