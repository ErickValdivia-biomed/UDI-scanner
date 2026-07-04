package io.github.erickvaldivia.udiscanner.model

import java.time.LocalDate

/**
 * Production Identifiers extracted from a UDI.
 */
data class ProductionIdentifiers(

    /**
     * Manufacturer's lot or batch number.
     */
    val lotNumber: String? = null,

    /**
     * Device serial number.
     */
    val serialNumber: String? = null,

    /**
     * Manufacturing date.
     */
    val manufacturingDate: LocalDate? = null,

    /**
     * Expiration date.
     */
    val expirationDate: LocalDate? = null

)