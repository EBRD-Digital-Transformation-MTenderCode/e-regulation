package com.procurement.regulation.dao

import com.datastax.driver.core.Session
import com.datastax.driver.core.querybuilder.QueryBuilder.eq
import com.datastax.driver.core.querybuilder.QueryBuilder.select
import com.procurement.regulation.exception.ErrorException
import com.procurement.regulation.exception.ErrorType
import org.springframework.stereotype.Service
import java.util.*

@Service
class TemplateDao(private val session: Session) {

    fun getTemplate(country: String, pmd: String, language: String, templateId: String): String {
        val query = select()
                .column(TEMPLATE)
                .from(TABLE)
                .where(eq(COUNTRY, country))
                .and(eq(PMD, pmd))
                .and(eq(LANGUAGE, language))
                .and(eq(TEMPLATE_ID, templateId))
                .limit(1)
        val row = session.execute(query).one() ?: throw ErrorException(ErrorType.TEMPLATE_NOT_FOUND)
        return row.getString(TEMPLATE)
    }


    fun getTemplates(country: String, pmd: String, language: String, templateIds: LinkedList<String>): LinkedList<String> {
        val templates = LinkedList<String>()
        templateIds.forEach { templateId ->
          templates.add(getTemplate(country, pmd, language, templateId))
        }
        return templates
    }

    companion object {
        private const val TABLE = "regulation_templates"
        private const val COUNTRY = "country"
        private const val PMD = "pmd"
        private const val LANGUAGE = "language"
        private const val TEMPLATE_ID = "template_id"
        private const val TEMPLATE = "template"
    }
}