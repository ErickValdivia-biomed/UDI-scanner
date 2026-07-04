package io.github.erickvaldivia.udiscanner.parser.ifa

import io.github.erickvaldivia.udiscanner.model.ProductionIdentifiers

/**
 * Result obtained after extracting
 * the IFA data elements.
 */
internal data class IFAExtractionResult(

    val deviceIdentifier: String,

    val productionIdentifiers: ProductionIdentifiers

)