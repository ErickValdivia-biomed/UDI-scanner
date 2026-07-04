package io.github.erickvaldivia.udiscanner.parser.hibcc

/**
 * Splits a HIBCC barcode into its constituent data structures.
 */
internal class HIBCCTokenizer {

    fun tokenize(
        barcode: String
    ): List<HIBCCToken> {

        if (barcode.isBlank()) {

            throw HIBCCParsingException(
                "Empty HIBCC barcode."
            )

        }

        /*
         * The last character of a HIBCC barcode is the
         * message check character. It does not belong to
         * any data field, so remove it before tokenization.
         */
        val normalizedBarcode = barcode.dropLast(1)

        val tokens = mutableListOf<HIBCCToken>()

        val sections = normalizedBarcode.split('/')

        tokens += readPrimaryStructure(
            sections.first()
        )

        for (i in 1 until sections.size) {

            tokens += readSection(
                sections[i]
            )

        }

        return tokens

    }

    private fun readPrimaryStructure(
        section: String
    ): HIBCCToken {

        if (!section.startsWith("+")) {

            throw HIBCCParsingException(
                "Primary Data Structure must begin with '+'."
            )

        }

        return HIBCCToken(

            identifier = HIBCCDataIdentifier.PRIMARY,

            value = section.substring(1)

        )

    }

    private fun readSection(
        section: String
    ): HIBCCToken {

        return when {

            section.startsWith("16D") ->

                HIBCCToken(

                    identifier = HIBCCDataIdentifier.MANUFACTURING_DATE,

                    value = section.substring(3)

                )

            section.startsWith("14D") ->

                HIBCCToken(

                    identifier = HIBCCDataIdentifier.EXPIRATION_DATE,

                    value = section.substring(3)

                )

            section.startsWith("S") ->

                HIBCCToken(

                    identifier = HIBCCDataIdentifier.SERIAL_NUMBER,

                    value = section.substring(1)

                )

            section.startsWith("Q") ->

                HIBCCToken(

                    identifier = HIBCCDataIdentifier.QUANTITY,

                    value = section.substring(1)

                )

            else ->

                HIBCCToken(

                    identifier = HIBCCDataIdentifier.SECONDARY,

                    value = section

                )

        }

    }

}