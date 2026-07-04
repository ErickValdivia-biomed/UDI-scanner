package io.github.erickvaldivia.udiscanner.parser.ifa

import io.github.erickvaldivia.udiscanner.model.IdentifiedBarcode
import io.github.erickvaldivia.udiscanner.model.Issuer
import io.github.erickvaldivia.udiscanner.model.ParsedUDI
import io.github.erickvaldivia.udiscanner.model.ProductionIdentifiers
import io.github.erickvaldivia.udiscanner.parser.Parser
import java.time.LocalDate

/**
 * Parses IFA UDI barcodes into a ParsedUDI.
 */
internal class IFAParser : Parser {

    override val issuer = Issuer.IFA

    private val tokenizer = IFATokenizer()

    override fun parse(
        barcode: IdentifiedBarcode
    ): ParsedUDI {

        val tokens = tokenizer.tokenize(
            barcode.rawBarcode
        )

        val extraction = extract(tokens)

        return ParsedUDI(

            issuer = Issuer.IFA,

            carrier = barcode.carrier,

            rawBarcode = barcode.rawBarcode,

            deviceIdentifier = extraction.deviceIdentifier,

            productionIdentifiers = extraction.productionIdentifiers

        )

    }

    private fun extract(
        tokens: List<IFAToken>
    ): IFAExtractionResult {

        var deviceIdentifier: String? = null

        var lotNumber: String? = null

        var serialNumber: String? = null

        var manufacturingDate: LocalDate? = null

        var expirationDate: LocalDate? = null

        for (token in tokens) {

            when (token.identifier) {

                IFADataIdentifier.DEVICE_IDENTIFIER ->

                    deviceIdentifier = token.value

                IFADataIdentifier.LOT_NUMBER ->

                    lotNumber = token.value

                IFADataIdentifier.SERIAL_NUMBER ->

                    serialNumber = token.value

                IFADataIdentifier.MANUFACTURING_DATE ->

                    manufacturingDate =
                        parseYYYYMMDD(token.value)

                IFADataIdentifier.EXPIRATION_DATE ->

                    expirationDate =
                        parseYYMMDD(token.value)

                IFADataIdentifier.QUANTITY -> {

                }
            }

        }

        return IFAExtractionResult(

            deviceIdentifier = deviceIdentifier
                ?: throw IFAParsingException(
                    "Missing Device Identifier."
                ),

            productionIdentifiers = ProductionIdentifiers(

                lotNumber = lotNumber,

                serialNumber = serialNumber,

                manufacturingDate = manufacturingDate,

                expirationDate = expirationDate

            )

        )

    }
    /**
     * Parses an IFA date.
     *
     * For the time being assumes ISO basic format YYYYMMDD.
     */
    private fun parseYYMMDD(
        value: String
    ): LocalDate {

        require(value.length == 6)

        val year = 2000 + value.substring(0,2).toInt()

        val month = value.substring(2,4).toInt()

        val day = value.substring(4,6).toInt()

        return LocalDate.of(
            year,
            month,
            day
        )

    }
    private fun parseYYYYMMDD(
        value: String
    ): LocalDate {

        require(value.length == 8)

        val year = value.substring(0,4).toInt()

        val month = value.substring(4,6).toInt()

        val day = value.substring(6,8).toInt()

        return LocalDate.of(
            year,
            month,
            day
        )

    }

}