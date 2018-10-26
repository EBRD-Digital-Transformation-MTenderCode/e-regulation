package com.procurement.regulation.dao

import com.datastax.driver.core.Session
import com.datastax.driver.core.querybuilder.QueryBuilder.*
import com.procurement.regulation.model.entity.DataEntity
import org.springframework.stereotype.Service

@Service
class DataDao(private val session: Session) {

    fun save(entity: DataEntity) {
        val insert = insertInto(TABLE)
        insert.value(ID, entity.contractId)
                .value(JSON_DATA, entity.jsonData)
        session.execute(insert)
    }

    fun getById(contractId: String): DataEntity? {
        val query = select()
                .all()
                .from(TABLE)
                .where(eq(ID, contractId)).limit(1)
        val row = session.execute(query).one()
        return if (row != null) DataEntity(
                row.getString(ID),
                row.getString(JSON_DATA)) else null
    }

    companion object {
        private val TABLE = "regulation_data"
        private val ID = "contract_id"
        private val JSON_DATA = "json_data"
    }
}