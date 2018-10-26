package com.procurement.regulation.model.dto.ocds

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue
import com.procurement.regulation.exception.EnumException
import java.util.*

enum class MainProcurementCategory constructor(private val value: String) {
    GOODS("goods"),
    WORKS("works"),
    SERVICES("services");

    override fun toString(): String {
        return this.value
    }

    @JsonValue
    fun value(): String {
        return this.value
    }

    companion object {

        private val CONSTANTS = HashMap<String, MainProcurementCategory>()

        init {
            for (c in values()) {
                CONSTANTS[c.value] = c
            }
        }

        @JsonCreator
        fun fromValue(value: String): MainProcurementCategory {
            return CONSTANTS[value]
                    ?: throw EnumException(MainProcurementCategory::class.java.name, value, Arrays.toString(values()))
        }
    }
}