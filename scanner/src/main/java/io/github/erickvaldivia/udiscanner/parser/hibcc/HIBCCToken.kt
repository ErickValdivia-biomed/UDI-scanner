package io.github.erickvaldivia.udiscanner.parser.hibcc

/**
 * Represents one HIBCC data structure.
 */
internal data class HIBCCToken(

    val identifier: HIBCCDataIdentifier,

    val value: String

)