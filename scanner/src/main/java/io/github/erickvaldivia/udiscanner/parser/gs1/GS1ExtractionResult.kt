package io.github.erickvaldivia.udiscanner.parser.gs1

import io.github.erickvaldivia.udiscanner.model.ProductionIdentifiers

/**
 * Result of extracting the information contained in a GS1 barcode.
 */
internal data class GS1ExtractionResult(

    /**
     * Device Identifier (DI).
     */
    val deviceIdentifier: String?,

    /**
     * Production Identifiers.
     */
    val productionIdentifiers: ProductionIdentifiers

)