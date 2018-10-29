package com.procurement.regulation.service

import com.procurement.regulation.dao.TemplateDao
import com.procurement.regulation.model.dto.bpe.templates.AgreedMetric
import com.procurement.regulation.model.dto.ocds.MainProcurementCategory

import com.procurement.regulation.model.dto.templates.TemplateRule
import com.procurement.regulation.utils.toObject
import org.springframework.stereotype.Service

@Service
class TemplateService(private val rulesService: RulesService,
                      private val templateDao: TemplateDao) {

    fun getStaticMetrics(country: String, pmd: String, language: String, mainProcurementCategory: String): List<AgreedMetric> {
        when (MainProcurementCategory.fromValue(mainProcurementCategory)) {
            MainProcurementCategory.GOODS -> {
                val rule = rulesService.getRule(country, pmd, GOODS_STATIC)
                val templateRule = toObject(TemplateRule::class.java, rule)
                val templates = templateDao.getTemplates(country, pmd, language, templateRule.templateIds)
                val metrics = mutableListOf<AgreedMetric>()
                templates.forEach { template ->
                    metrics.add(toObject(AgreedMetric::class.java, template))
                }
                return metrics
            }
            MainProcurementCategory.SERVICES -> {
                val rule = rulesService.getRule(country, pmd, SERVICES_STATIC)
                val templateRule = toObject(TemplateRule::class.java, rule)
                val templates = templateDao.getTemplates(country, pmd, language, templateRule.templateIds)
                val metrics = mutableListOf<AgreedMetric>()
                templates.forEach { template ->
                    metrics.add(toObject(AgreedMetric::class.java, template))
                }
                return metrics
            }
            MainProcurementCategory.WORKS -> {
                val rule = rulesService.getRule(country, pmd, WORKS_STATIC)
                val templateRule = toObject(TemplateRule::class.java, rule)
                val templates = templateDao.getTemplates(country, pmd, language, templateRule.templateIds)
                val metrics = mutableListOf<AgreedMetric>()
                templates.forEach { template ->
                    metrics.add(toObject(AgreedMetric::class.java, template))
                }
                return metrics
            }
        }
    }

    fun getDynamicMetrics(country: String, pmd: String, language: String, mainProcurementCategory: String): List<AgreedMetric> {
        when (MainProcurementCategory.fromValue(mainProcurementCategory)) {
            MainProcurementCategory.GOODS -> {
                val rule = rulesService.getRule(country, pmd, GOODS_DYNAMIC)
                val templateRule = toObject(TemplateRule::class.java, rule)
                val templates = templateDao.getTemplates(country, pmd, language, templateRule.templateIds)
                val metrics = mutableListOf<AgreedMetric>()
                templates.forEach { template ->
                    metrics.add(toObject(AgreedMetric::class.java, template))
                }
                return metrics
            }
            MainProcurementCategory.SERVICES -> {
                val rule = rulesService.getRule(country, pmd, SERVICES_DYNAMIC)
                val templateRule = toObject(TemplateRule::class.java, rule)
                val templates = templateDao.getTemplates(country, pmd, language, templateRule.templateIds)
                val metrics = mutableListOf<AgreedMetric>()
                templates.forEach { template ->
                    metrics.add(toObject(AgreedMetric::class.java, template))
                }
                return metrics
            }
            MainProcurementCategory.WORKS -> {
                val rule = rulesService.getRule(country, pmd, WORKS_DYNAMIC)
                val templateRule = toObject(TemplateRule::class.java, rule)
                val templates = templateDao.getTemplates(country, pmd, language, templateRule.templateIds)
                val metrics = mutableListOf<AgreedMetric>()
                templates.forEach { template ->
                    metrics.add(toObject(AgreedMetric::class.java, template))
                }
                return metrics
            }
        }

    }

    companion object {
        private const val GOODS_STATIC = "goods_static"
        private const val GOODS_DYNAMIC = "goods_dynamic"
        private const val SERVICES_STATIC = "services_static"
        private const val SERVICES_DYNAMIC = "services_dynamic"
        private const val WORKS_STATIC = "works_static"
        private const val WORKS_DYNAMIC = "works_dynamic"
    }
}
