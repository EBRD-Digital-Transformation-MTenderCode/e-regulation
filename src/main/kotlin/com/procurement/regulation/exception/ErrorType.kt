package com.procurement.regulation.exception

enum class ErrorType constructor(val code: String, val message: String) {
    JSON_TYPE("00.00", "Invalid type: "),
    DATA_NOT_FOUND("00.01", "Data not found."),
    INVALID_JSON_REQUIRED("10.01", "Must be non-null: "),
    INVALID_JSON_EMPTY("10.02", "Must be not-empty: "),
    INVALID_JSON_TYPE("10.03", "Invalid type: "),
    INVALID_JSON_VALUE("10.04", "Invalid value: "),
    CONTEXT("20.01", "Context parameter not found.");
}
