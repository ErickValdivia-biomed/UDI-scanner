package io.github.erickvaldivia.udiscanner.parser.hibcc

import io.github.erickvaldivia.udiscanner.model.IdentifiedBarcode
import io.github.erickvaldivia.udiscanner.model.Issuer
import io.github.erickvaldivia.udiscanner.model.ParsedUDI
import io.github.erickvaldivia.udiscanner.model.ProductionIdentifiers
import io.github.erickvaldivia.udiscanner.parser.Parser
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
 * Parses HIBCC barcodes into a ParsedUDI.
 */
internal class HIBCCParser : Parser {

    override val issuer = Issuer.HIBCC

    private val tokenizer = HIBCCTokenizer()

    private val hibccDateFormatter =
        DateTimeFormatter.ofPattern("yyyyMMdd")

    override fun parse(
        barcode: IdentifiedBarcode
    ): ParsedUDI {

        val tokens = tokenizer.tokenize(
            barcode.rawBarcode
        )

        val extraction = extract(tokens)

        return ParsedUDI(

            issuer = Issuer.HIBCC,

            carrier = barcode.carrier,

            rawBarcode = barcode.rawBarcode,

            deviceIdentifier = extraction.deviceIdentifier,

            productionIdentifiers = extraction.productionIdentifiers

        )

    }

    private fun extract(
        tokens: List<HIBCCToken>
    ): HIBCCExtractionResult {

        var deviceIdentifier: String? = null

        var lotNumber: String? = null

        var serialNumber: String? = null

        var manufacturingDate: LocalDate? = null

        var expirationDate: LocalDate? = null

        for (token in tokens) {

            when (token.identifier) {

                HIBCCDataIdentifier.PRIMARY ->

                    deviceIdentifier =
                        extractDeviceIdentifier(
                            token.value
                        )

                HIBCCDataIdentifier.SECONDARY ->

                    lotNumber =
                        extractLotNumber(
                            token.value
                        )

                HIBCCDataIdentifier.SERIAL_NUMBER ->

                    serialNumber = token.value

                HIBCCDataIdentifier.MANUFACTURING_DATE ->

                    manufacturingDate =
                        LocalDate.parse(
                            token.value,
                            hibccDateFormatter
                        )

                HIBCCDataIdentifier.EXPIRATION_DATE ->

                    expirationDate =
                        LocalDate.parse(
                            token.value,
                            hibccDateFormatter
                        )

                HIBCCDataIdentifier.QUANTITY -> {

                    // Ignored by ParsedUDI.

                }

            }

        }

        return HIBCCExtractionResult(

            deviceIdentifier = deviceIdentifier,

            productionIdentifiers = ProductionIdentifiers(

                lotNumber = lotNumber,

                serialNumber = serialNumber,

                manufacturingDate = manufacturingDate,

                expirationDate = expirationDate

            )

        )

    }
    /**
     * Extracts the Universal Product Number (UPN) from the
     * Primary Data Structure.
     */
    private fun extractDeviceIdentifier(
        primary: String
    ): String {

        if (primary.length < 6) {

            throw HIBCCParsingException(
                "Invalid Primary Data Structure."
            )

        }

        return primary.dropLast(1)

    }
    /**
     * Extracts the Lot Number from the Secondary Data Structure.
     */
    private fun extractLotNumber(
        secondary: String
    ): String? {

        val fields = secondary.split('#')

        if (fields.size < 3) {
            return null
        }

        return fields[2]
            .takeIf { it.isNotBlank() }

    }

}