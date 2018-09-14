package com.procurement.regulation.service

import com.datastax.driver.core.utils.UUIDs
import com.procurement.regulation.utils.milliNowUTC
import org.springframework.stereotype.Service
import java.util.*

interface GenerationService {

    fun generateRandomUUID(): UUID

    fun getNowUtc(): Long
}

@Service
class GenerationServiceImpl : GenerationService {

    override fun generateRandomUUID(): UUID {
        return UUIDs.random()
    }

    override fun getNowUtc(): Long {
        return milliNowUTC()
    }
}