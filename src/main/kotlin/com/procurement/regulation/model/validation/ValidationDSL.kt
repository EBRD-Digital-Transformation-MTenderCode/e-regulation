package com.rest

import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.JsonNodeType
import com.procurement.regulation.exception.ErrorException
import com.procurement.regulation.exception.ErrorType
import com.procurement.regulation.utils.createArrayNode
import com.procurement.regulation.utils.createObjectNode
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException
import java.time.temporal.ChronoField

object DateFormatter {
    val formatter: DateTimeFormatter = DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .append(DateTimeFormatter.ISO_LOCAL_DATE)
            .appendLiteral('T')
            .appendValue(ChronoField.HOUR_OF_DAY, 2)
            .appendLiteral(':')
            .appendValue(ChronoField.MINUTE_OF_HOUR, 2)
            .optionalStart()
            .appendLiteral(':')
            .appendValue(ChronoField.SECOND_OF_MINUTE, 2)
            .appendLiteral('Z')
            .toFormatter()
}

interface Rule {
    val name: String
}

open class obj(override var name: String = "",
               var nonNull: Boolean = false,
               var rules: List<Rule> = listOf()) : Rule {

    open fun validate(json: JsonNode?) {
        checkRequired(json)
        json?.let {
            checkType(json)
            for (rule in rules) {
                val value = json.get(rule.name)
                when (rule) {
                    is obj -> rule.validate(value)
                    is arr -> rule.validate(value)
                    is str -> rule.validate(value)
                    is num -> rule.validate(value)
                    is enum -> rule.validate(value)
                    is bool -> rule.validate(value)
                    is date -> rule.validate(value)
                    is budget -> rule.validate(value)
                }
            }
        }
    }

    open fun filter(json: JsonNode?): JsonNode {
        val result = createObjectNode()
        json?.let {
            for (rule in rules) {
                val value = json.get(rule.name)
                when (rule) {
                    is obj -> result.replace(rule.name, rule.filter(value))
                    is arr -> result.replace(rule.name, rule.filter(value))
                    else -> result.replace(rule.name, value)
                }
            }
        }
        return result
    }

    private fun checkRequired(json: JsonNode?) {
        if (json == null && nonNull) {
            throw ErrorException(ErrorType.INVALID_JSON_REQUIRED, name)
        }
    }

    private fun checkType(json: JsonNode) {
        if (json.nodeType != JsonNodeType.OBJECT) {
            throw ErrorException(ErrorType.INVALID_JSON_TYPE, name)
        }
    }
}


class arr(override var name: String = "",
          var nonNull: Boolean = false,
          var notEmpty: Boolean = false,
          var rule: Rule = obj()) : Rule {

    fun validate(json: JsonNode?) {
        checkRequired(json)
        json?.let {
            checkValue(it)
            checkType(it)
            for (item in it) {
                checkRuleType(rule, item)
            }
        }
    }

    open fun filter(json: JsonNode?): ArrayNode {
        val result = createArrayNode()
        json?.let {
            for (item in it) {
                when (rule) {
                    is obj -> result.add((rule as obj).filter(item))
                    is arr -> result.add((rule as arr).filter(item))
                    else -> result.add(item)
                }
            }
        }
        return result
    }


    private fun checkRequired(json: JsonNode?) {
        if (json == null && nonNull) {
            throw ErrorException(ErrorType.INVALID_JSON_REQUIRED, name)
        }
    }

    private fun checkValue(json: JsonNode) {
        if (json.size() == 0 && notEmpty) {
            throw ErrorException(ErrorType.INVALID_JSON_EMPTY, name)
        }
    }

    private fun checkType(json: JsonNode) {
        if (json.nodeType != JsonNodeType.ARRAY) {
            throw ErrorException(ErrorType.INVALID_JSON_TYPE, name)
        }
    }

    private fun checkRuleType(rule: Rule, json: JsonNode) {
        when (rule) {
            is obj -> rule.validate(json)
            is arr -> rule.validate(json)
            is str -> rule.validate(json)
            is num -> rule.validate(json)
            is enum -> rule.validate(json)
            is bool -> rule.validate(json)
            is date -> rule.validate(json)
        }
    }
}

class budget(name: String = "",
             nonNull: Boolean = false,
             rules: List<Rule> = listOf()) : obj(name, nonNull, rules) {

    override fun validate(json: JsonNode?) {
        json?.let {
            val isEuropeanUnionFunded = it.get("isEuropeanUnionFunded").asBoolean()
            val europeanUnionFunding = it.get("europeanUnionFunding")
            if (isEuropeanUnionFunded && europeanUnionFunding == null) {
                throw ErrorException(ErrorType.INVALID_JSON_REQUIRED, "europeanUnionFunding")
            }
            super.validate(it)
        }
    }
}


class str(override var name: String = "",
          var nonNull: Boolean = false,
          var notEmpty: Boolean = false) : Rule {

    fun validate(json: JsonNode?) {
        checkRequired(json)
        json?.let {
            checkType(json)
            checkValue(json)
        }
    }

    private fun checkRequired(json: JsonNode?) {
        if (json == null && nonNull) {
            throw ErrorException(ErrorType.INVALID_JSON_REQUIRED, name)
        }
    }

    private fun checkType(json: JsonNode) {
        if (json.nodeType != JsonNodeType.STRING) {
            throw ErrorException(ErrorType.INVALID_JSON_TYPE, name)
        }
    }

    private fun checkValue(json: JsonNode) {
        if (json.asText().isEmpty() && notEmpty) {
            throw ErrorException(ErrorType.INVALID_JSON_EMPTY, name)
        }
    }
}

class bool(override var name: String = "",
           var nonNull: Boolean = false) : Rule {

    fun validate(json: JsonNode?) {
        checkRequired(json)
        json?.let {
            checkType(json)
        }
    }

    private fun checkRequired(json: JsonNode?) {
        if (json == null && nonNull) {
            throw ErrorException(ErrorType.INVALID_JSON_REQUIRED, name)
        }
    }

    private fun checkType(json: JsonNode) {
        if (json.nodeType != JsonNodeType.BOOLEAN) {
            throw ErrorException(ErrorType.INVALID_JSON_TYPE, name)
        }
    }
}

class num(override var name: String = "",
          var nonNull: Boolean = false) : Rule {

    fun validate(json: JsonNode?) {
        checkRequired(json)
        json?.let {
            checkType(json)

        }
    }

    private fun checkRequired(json: JsonNode?) {
        if (json == null && nonNull) {
            throw ErrorException(ErrorType.INVALID_JSON_REQUIRED, name)
        }
    }

    private fun checkType(json: JsonNode) {
        if (json.nodeType != JsonNodeType.NUMBER) {
            throw ErrorException(ErrorType.INVALID_JSON_TYPE, name)
        }
    }
}

class enum(override var name: String = "",
           var nonNull: Boolean = false,
           values: Array<*> = arrayOf("")) : Rule {

    private val valuesList: List<String> = values.asSequence().map { it.toString() }.toList()

    fun validate(json: JsonNode?) {
        checkRequired(json)
        json?.let {
            checkType(it)
            checkValue(it)
        }
    }

    private fun checkType(json: JsonNode) {
        if (json.nodeType != JsonNodeType.STRING) {
            throw ErrorException(ErrorType.INVALID_JSON_TYPE, name)
        }
    }

    private fun checkValue(json: JsonNode?) {
        json?.let {
            if (!valuesList.contains(it.asText())) {
                throw ErrorException(ErrorType.INVALID_JSON_VALUE, name + ": " + it.asText())
            }
        }
    }

    private fun checkRequired(json: JsonNode?) {
        if (json == null && nonNull) {
            throw ErrorException(ErrorType.INVALID_JSON_REQUIRED, name)
        }
    }
}

class date(override var name: String = "",
           var nonNull: Boolean = false) : Rule {

    fun validate(json: JsonNode?) {
        checkRequired(json)
        json?.let {
            checkType(json)
            checkValue(json)
        }
    }

    private fun checkType(json: JsonNode) {
        if (json.nodeType != JsonNodeType.STRING) {
            throw ErrorException(ErrorType.INVALID_JSON_TYPE, name)
        }
    }

    private fun checkRequired(json: JsonNode?) {
        if (json == null && nonNull) {
            throw ErrorException(ErrorType.INVALID_JSON_REQUIRED, name)
        }
    }

    private fun checkValue(json: JsonNode) {
        try {
            LocalDateTime.parse(json.asText(), DateFormatter.formatter)
        } catch (ex: DateTimeParseException) {
            throw ErrorException(ErrorType.INVALID_JSON_TYPE, name)
        }
    }
}


fun <T> rules(element: T): List<T> = java.util.Collections.singletonList(element)

fun <T> rules(vararg elements: T): List<T> = if (elements.size > 0) elements.asList() else emptyList()
