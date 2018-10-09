package com.procurement.regulation.dao

import com.datastax.driver.core.Session
import com.datastax.driver.core.querybuilder.QueryBuilder
import com.datastax.driver.core.querybuilder.QueryBuilder.*
import com.datastax.driver.core.utils.UUIDs
import com.procurement.regulation.model.entity.Entity
import com.procurement.regulation.utils.milliNowUTC
import com.procurement.regulation.utils.nowUTC
import org.springframework.stereotype.Service
import java.util.*

interface MainDao {

    fun save(entity: Entity)

    fun getByCpIdAndToken(cpId: String, token: UUID): Entity?

    fun getByCpId(cpId: String): Entity?

}

@Service
class MainDaoImpl(private val session: Session) : MainDao {

    override fun save(entity: Entity) {
        val insert = insertInto(EI_TABLE)
        insert.value(CP_ID, entity.cpId)
                .value(TOKEN, entity.token)
                .value(OWNER, entity.owner)
                .value(CREATED_DATE, entity.createdDate)
                .value(JSON_DATA, entity.jsonData)
        session.execute(insert)
    }

    override fun getByCpIdAndToken(cpId: String, token: UUID): Entity? {
        val query = select()
                .all()
                .from(EI_TABLE)
                .where(eq(CP_ID, cpId))
                .and(eq(TOKEN, token)).limit(1)
        val row = session.execute(query).one()
        return if (row != null) Entity(
                row.getString(CP_ID),
                row.getUUID(TOKEN),
                row.getString(OWNER),
                row.getTimestamp(CREATED_DATE),
                row.getString(JSON_DATA)) else null
    }

    override fun getByCpId(cpId: String): Entity? {
        val query = select()
                .all()
                .from(EI_TABLE)
                .where(eq(CP_ID, cpId)).limit(1)
        val row = session.execute(query).one()
        return if (row != null) Entity(
                row.getString(CP_ID),
                row.getUUID(TOKEN),
                row.getString(OWNER),
                row.getTimestamp(CREATED_DATE),
                row.getString(JSON_DATA)) else null
    }

    companion object {
        private val EI_TABLE = "budget_ei"
        private val CP_ID = "cp_id"
        private val TOKEN = "token_entity"
        private val OWNER = "owner"
        private val CREATED_DATE = "created_date"
        private val JSON_DATA = "json_data"
    }
}
