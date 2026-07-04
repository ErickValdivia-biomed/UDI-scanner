package io.github.erickvaldivia.udiscanner.parser.hibcc

import io.github.erickvaldivia.udiscanner.model.ProductionIdentifiers

/**
 * Result of extracting the information contained in a HIBCC barcode.
 */
internal data class HIBCCExtractionResult(

    val deviceIdentifier: String?,

    val productionIdentifiers: ProductionIdentifiers

)