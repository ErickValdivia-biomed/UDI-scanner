package io.github.erickvaldivia.udiscanner.parser.hibcc

/**
 * Result of reading a HIBCC data structure.
 */
internal data class HIBCCStructure(

    val value: String,

    val nextCursor: Int

)