package io.github.erickvaldivia.udiscanner.parser

import io.github.erickvaldivia.udiscanner.model.IdentifiedBarcode
import io.github.erickvaldivia.udiscanner.model.Issuer
import io.github.erickvaldivia.udiscanner.model.ParsedUDI
import io.github.erickvaldivia.udiscanner.parser.gs1.GS1Parser
import io.github.erickvaldivia.udiscanner.parser.hibcc.HIBCCParser
import io.github.erickvaldivia.udiscanner.parser.ifa.IFAParser

/**
 * Dispatches barcode parsing to the appropriate issuer parser.
 */
internal class ParserManager {

    /**
     * Available parsers.
     */
    private val parsers = mapOf(

        Issuer.GS1 to GS1Parser(),

        Issuer.HIBCC to HIBCCParser(),

        Issuer.IFA to IFAParser()

    )

    /**
     * Parses an identified barcode.
     */
    fun parse(
        barcode: IdentifiedBarcode
    ): ParsedUDI {

        val parser =

            parsers[barcode.issuer]

                ?: throw IllegalArgumentException(
                    "Unsupported issuer: ${barcode.issuer}"
                )

        return parser.parse(barcode)

    }

}