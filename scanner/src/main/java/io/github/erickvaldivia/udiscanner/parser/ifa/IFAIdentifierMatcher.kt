package io.github.erickvaldivia.udiscanner.parser.ifa

/**
 * Matches ANSI MH10 Data Identifiers.
 */
internal object IFAIdentifierMatcher {

    /**
     * Known identifiers ordered from
     * longest to shortest.
     */
    private val identifiers = listOf(

        IFAConstants.MANUFACTURING,

        IFAConstants.DEVICE_IDENTIFIER,

        IFAConstants.LOT,

        IFAConstants.SERIAL,

        IFAConstants.EXPIRATION

    )

    /**
     * Finds the identifier located at
     * the given cursor.
     */
    fun match(
        barcode: String,
        cursor: Int
    ): String? {

        return identifiers.firstOrNull {

            barcode.startsWith(
                prefix = it,
                startIndex = cursor
            )

        }

    }

}