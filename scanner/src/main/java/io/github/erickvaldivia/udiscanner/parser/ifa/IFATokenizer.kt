package io.github.erickvaldivia.udiscanner.parser.ifa

/**
 * Splits an IFA UDI into ANSI MH10 data elements.
 */
internal class IFATokenizer {

    /**
     * Tokenizes an IFA barcode.
     */
    fun tokenize(
        barcode: String
    ): List<IFAToken> {

        val tokens = mutableListOf<IFAToken>()

        val fields = barcode.split('\u001D')

        for (field in fields) {

            if (field.isBlank()) {
                continue
            }

            tokens += parseField(field)

        }

        return tokens

    }
    private fun parseField(
        field: String
    ): IFAToken {

        val identifier =

            IFAIdentifierMatcher.match(
                field,
                0
            )

                ?: throw IFAParsingException(
                    "Unknown Data Identifier: $field"
                )

        val value =
            field.substring(identifier.length)

        return IFAToken(

            identifier = toIdentifier(identifier),

            value = value

        )

    }
    /**
     * Reads the next MH10 data element.
     */
    /**
     * Reads the next MH10 data element.
     */
//    private fun readNextToken(
//        barcode: String,
//        cursor: Int
//    ): IFATokenResult {
//
//        val identifier =
//
//            IFAIdentifierMatcher.match(
//                barcode,
//                cursor
//            )
//
//                ?: throw IFAParsingException(
//                    "Unknown Data Identifier at position $cursor."
//                )
//
//        val valueStart =
//
//            cursor + identifier.length
//
//        val valueEnd =
//
//            findValueEnd(
//                barcode,
//                valueStart
//            )
//
//        val value =
//
//            barcode.substring(
//                valueStart,
//                valueEnd
//            )
//
//        var nextCursor = valueEnd
//
//        if (
//
//            nextCursor < barcode.length &&
//            barcode[nextCursor] == '\u001D'
//
//        ) {
//
//            nextCursor++
//
//        }
//
//        println("Identifier = '$identifier'")
//        println("Value      = '$value'")
//        println("NextCursor = $nextCursor")
//
//        return IFATokenResult(
//
//            token = IFAToken(
//
//                identifier = toIdentifier(identifier),
//
//                value = value
//
//            ),
//
//            nextCursor = nextCursor
//
//        )
//
//    }
//    private fun findValueEnd(
//        barcode: String,
//        start: Int
//    ): Int {
//
//        var cursor = start
//
//        while (cursor < barcode.length) {
//
//            if (barcode[cursor] == '\u001D') {
//                return cursor
//            }
//
//            if (
//
//                IFAIdentifierMatcher.match(
//                    barcode,
//                    cursor
//                ) != null
//
//            ) {
//
//                return cursor
//
//            }
//
//            cursor++
//
//        }
//
//        return barcode.length
//
//    }
    private fun toIdentifier(
        identifier: String
    ): IFADataIdentifier =
        when (identifier) {

            IFAConstants.DEVICE_IDENTIFIER ->
                IFADataIdentifier.DEVICE_IDENTIFIER

            IFAConstants.LOT ->
                IFADataIdentifier.LOT_NUMBER

            IFAConstants.SERIAL ->
                IFADataIdentifier.SERIAL_NUMBER

            IFAConstants.EXPIRATION ->
                IFADataIdentifier.EXPIRATION_DATE

            IFAConstants.MANUFACTURING ->
                IFADataIdentifier.MANUFACTURING_DATE

            else ->

                throw IFAParsingException(
                    "Unsupported Data Identifier: $identifier."
                )

        }

}