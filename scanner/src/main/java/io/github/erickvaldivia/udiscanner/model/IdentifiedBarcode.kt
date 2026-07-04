package io.github.erickvaldivia.udiscanner.model

/**
 * Represents a recognized barcode whose issuing system
 * has already been identified.
 */
data class IdentifiedBarcode(

    /**
     * Raw decoded barcode.
     */
    val rawBarcode: String,

    /**
     * Physical barcode symbology.
     */
    val carrier: Carrier,

    /**
     * Identified issuing organization.
     */
    val issuer: Issuer

)