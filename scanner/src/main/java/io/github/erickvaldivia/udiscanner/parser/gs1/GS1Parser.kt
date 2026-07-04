package io.github.erickvaldivia.udiscanner.parser.gs1

import io.github.erickvaldivia.udiscanner.model.IdentifiedBarcode
import io.github.erickvaldivia.udiscanner.model.Issuer
import io.github.erickvaldivia.udiscanner.model.ParsedUDI
import io.github.erickvaldivia.udiscanner.model.ProductionIdentifiers
import io.github.erickvaldivia.udiscanner.parser.Parser
import java.time.LocalDate

/**
 * Parses GS1 barcodes into a neutral ParsedUDI representation.
 *
 * This parser assumes the barcode has already been identified
 * as belonging to the GS1 issuing system.
 */
internal class GS1Parser : Parser {

    override val issuer = Issuer.GS1

    private val tokenizer = GS1Tokenizer()

    /**
     * Parses the supplied GS1 barcode.
     */
    override fun parse(
        barcode: IdentifiedBarcode
    ): ParsedUDI {

        val tokens = tokenizer.tokenize(
            barcode.rawBarcode
        )

        val extraction = extractProductionIdentifiers(
            tokens
        )

        return ParsedUDI(

            issuer = Issuer.GS1,

            carrier = barcode.carrier,

            rawBarcode = barcode.rawBarcode,

            deviceIdentifier = extraction.deviceIdentifier,

            productionIdentifiers = extraction.productionIdentifiers

        )

    }

    /**
     * Extracts the Device Identifier and Production Identifiers
     * from the GS1 token stream.
     */
    private fun extractProductionIdentifiers(
        tokens: List<GS1Token>
    ): GS1ExtractionResult {

        var deviceIdentifier: String? = null

        var lotNumber: String? = null

        var serialNumber: String? = null

        var manufacturingDate: LocalDate? = null

        var expirationDate: LocalDate? = null

        for (token in tokens) {

            when (token.applicationIdentifier) {

                GS1ApplicationIdentifier.DEVICE_IDENTIFIER ->

                    deviceIdentifier = token.value

                GS1ApplicationIdentifier.LOT_NUMBER ->

                    lotNumber = token.value

                GS1ApplicationIdentifier.SERIAL_NUMBER ->

                    serialNumber = token.value

                GS1ApplicationIdentifier.MANUFACTURING_DATE ->

                    manufacturingDate =
                        GS1DateParser.parse(token.value)

                GS1ApplicationIdentifier.EXPIRATION_DATE ->

                    expirationDate =
                        GS1DateParser.parse(token.value)
                //Those ones aren't needed for now.
                GS1ApplicationIdentifier.QUANTITY,
                GS1ApplicationIdentifier.ADDITIONAL_PRODUCT_IDENTIFICATION,
                GS1ApplicationIdentifier.CUSTOMER_PART_NUMBER,
                GS1ApplicationIdentifier.SECONDARY_SERIAL_NUMBER -> {
                    // Supported for tokenization but intentionally ignored.
                }
                else -> {
                    // Ignore unsupported AIs.
                }

            }

        }

        return GS1ExtractionResult(

            deviceIdentifier = deviceIdentifier,

            productionIdentifiers = ProductionIdentifiers(

                lotNumber = lotNumber,

                serialNumber = serialNumber,

                manufacturingDate = manufacturingDate,

                expirationDate = expirationDate

            )

        )

    }

}