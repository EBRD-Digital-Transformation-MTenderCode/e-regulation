package com.procurement.regulation.dao

import com.datastax.driver.core.Session
import com.datastax.driver.core.querybuilder.Insert
import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.querybuilder.QueryBuilder.*
import com.procurement.regulation.model.entity.TermsEntity
import org.springframework.stereotype.Service
import java.util.*

@Service
class TermsDao(private val session: Session) {

    fun getById(contractId: String): TermsEntity? {
        val query = select()
                .all()
                .from(TABLE)
                .where(eq(ID, contractId)).limit(1)
        val row = session.execute(query).one()
        return if (row != null) TermsEntity(
                row.getString(ID),
                row.getString(JSON_DATA)) else null
    }

    fun save(entity: TermsEntity) {
        val insert = insertInto(TABLE)
        insert.value(ID, entity.contractId)
                .value(JSON_DATA, entity.jsonData)
        session.execute(insert)
    }

    companion object {
        private val TABLE = "regulation_data"
        private val ID = "contract_id"
        private val JSON_DATA = "json_data"
    }
}