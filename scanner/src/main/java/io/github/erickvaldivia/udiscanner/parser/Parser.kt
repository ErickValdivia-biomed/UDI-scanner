package io.github.erickvaldivia.udiscanner.parser

import io.github.erickvaldivia.udiscanner.model.IdentifiedBarcode
import io.github.erickvaldivia.udiscanner.model.Issuer
import io.github.erickvaldivia.udiscanner.model.ParsedUDI

/**
 * Converts an identified barcode into a Parsed UDI.
 */
internal interface Parser {

    /**
     * Issuer supported by this parser.
     */
    val issuer: Issuer

    /**
     * Parses an identified barcode.
     */
    fun parse(
        barcode: IdentifiedBarcode
    ): ParsedUDI

}