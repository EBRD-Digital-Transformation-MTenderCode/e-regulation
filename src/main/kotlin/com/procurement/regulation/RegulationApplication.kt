package com.procurement.regulation

import com.procurement.regulation.config.ApplicationConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication(scanBasePackageClasses = [ApplicationConfig::class])
@EnableEurekaClient
class RegulationApplication

fun main(args: Array<String>) {
    runApplication<RegulationApplication>(*args)
}
