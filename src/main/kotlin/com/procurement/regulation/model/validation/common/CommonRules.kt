package com.procurement.regulation.model.validation.common

import com.rest.arr
import com.rest.obj
import com.rest.rules
import com.rest.str

val organizationReferenceRule = rules(
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
)