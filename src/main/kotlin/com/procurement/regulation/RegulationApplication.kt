package com.procurement.regulation

import com.procurement.regulation.config.ApplicationConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(scanBasePackageClasses = [ApplicationConfig::class])
class RegulationApplication

fun main(args: Array<String>) {
    runApplication<RegulationApplication>(*args)
}
