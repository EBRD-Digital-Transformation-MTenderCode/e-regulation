package com.procurement.regulation.model.validation.fs

import com.procurement.regulation.model.validation.common.organizationReferenceRule
import com.rest.*


fun getProcuringEntityRule(commonRules: Boolean = true): List<Rule> {

    if (commonRules) {
        return organizationReferenceRule
    } else {
        return rules(
                str("name", true),
                obj("identifier", true, rules(
                        str("scheme", true),
                        str("id", true),
                        str("legalName", true),
                        str("uri", true)
                )),
                arr("additionalIdentifiers", false, true,
                        obj("identifier", true, rules(
                                str("scheme", true),
                                str("id", true),
                                str("legalName", true),
                                str("uri", true)
                        ))),
                obj("address", true, rules(
                        str("streetAddress", true),
                        str("locality", true),
                        str("region", true),
                        str("postalCode"),
                        str("countryName", true)
                )),
                obj("contactPoint", true, rules(
                        str("name", true),
                        str("email", true),
                        str("telephone", true),
                        str("faxNumber"),
                        str("url", true)
                )))
    }
}


val createFsRule = obj("createFs", true, rules(
        obj("planning", true, rules(
                budget("regulation", true, rules(
                        bool("isEuropeanUnionFunded", true),
                        obj("europeanUnionFunding", false, rules(
                                str("projectName", true),
                                str("projectIdentifier", true),
                                str("uri", false)
                        )),
                        str("id"),
                        str("description"),
                        obj("period", true, rules(
                                date("startDate", true),
                                date("endDate")
                        )),
                        obj("amount", true, rules(
                                num("amount"),
                                enum("currency", true)
                        )),
                        str("rationale", false)
                ))
        )),
        obj("tender", true, rules(
                str("id", true),
                obj("procuringEntity", true, getProcuringEntityRule()),
                obj("buyer", false, rules(
                        str("name", true),
                        obj("identifier", true, rules(
                                str("scheme", true),
                                str("id", true),
                                str("legalName", true),
                                str("uri", true)
                        )),
                        arr("additionalIdentifiers", false, true,
                                obj("identifier", true, rules(
                                        str("scheme", true),
                                        str("id", true),
                                        str("legalName", true),
                                        str("uri", true)
                                ))),
                        obj("address", true, rules(
                                str("streetAddress", true),
                                str("locality", true),
                                str("region", true),
                                str("postalCode"),
                                str("countryName", true)
                        )),
                        obj("contactPoint", true, rules(
                                str("name", true),
                                str("email", true),
                                str("telephone", true),
                                str("faxNumber"),
                                str("url", true)
                        ))
                ))
        ))
))


