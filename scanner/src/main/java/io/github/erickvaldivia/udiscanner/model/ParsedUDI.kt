package io.github.erickvaldivia.udiscanner.model

/**
 * Represents a parsed UDI independently of its issuing system.
 */
data class ParsedUDI(

    /**
     * Issuing organization.
     */
    val issuer: Issuer,

    /**
     * Physical barcode carrier.
     */
    val carrier: Carrier,

    /**
     * Original decoded barcode.
     */
    val rawBarcode: String,

    /**
     * Device Identifier.
     */
    val deviceIdentifier: String?,

    /**
     * Production Identifiers.
     */
    val productionIdentifiers: ProductionIdentifiers

)