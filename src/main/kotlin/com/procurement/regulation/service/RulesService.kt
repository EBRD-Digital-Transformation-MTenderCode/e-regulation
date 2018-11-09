package com.procurement.regulation.service

import com.procurement.regulation.dao.RulesDao
import com.procurement.regulation.exception.ErrorException
import com.procurement.regulation.exception.ErrorType
import org.springframework.stereotype.Service

@Service
class RulesService(private val rulesDao: RulesDao) {

    fun getRule(country: String, pmd: String, parameter: String): String {
        return rulesDao.getValue(country, pmd, parameter) ?: throw ErrorException(ErrorType.RULES_NOT_FOUND)
    }
}
