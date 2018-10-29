package com.procurement.regulation.dao

import com.datastax.driver.core.Session
import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.querybuilder.QueryBuilder.eq
import com.datastax.driver.core.querybuilder.QueryBuilder.select
import com.procurement.regulation.model.entity.TemplateEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class TemplateDao(private val session: Session) {

    fun getTemplate(country: String, pmd: String, language: String, templateId: String): String? {
        val query = select()
                .column(TEMPLATE)
                .from(TABLE)
                .where(eq(COUNTRY, country))
                .and(eq(PMD, pmd))
                .and(eq(LANGUAGE, language))
                .and(eq(TEMPLATE_ID, templateId))
                .limit(1)
        val row = session.execute(query).one()
        return if (row != null) return row.getString(TEMPLATE)
        else null
    }


    fun getTemplates(country: String, pmd: String, language: String, templateIds: Set<String>): List<String> {
        val query = select()
                .all()
                .from(TABLE)
                .where(eq(COUNTRY, country))
                .and(eq(PMD, pmd))
                .and(eq(LANGUAGE, language))
                .and(QueryBuilder.`in`(TEMPLATE_ID, *templateIds.toTypedArray()))
        val resultSet = session.execute(query)
        val entities = ArrayList<TemplateEntity>()
        resultSet.forEach { row ->
            entities.add(TemplateEntity(
                    row.getString(COUNTRY),
                    row.getString(PMD),
                    row.getString(LANGUAGE),
                    row.getString(TEMPLATE_ID),
                    row.getString(TEMPLATE)))
        }
        return entities.asSequence().map { it.template }.toList()
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