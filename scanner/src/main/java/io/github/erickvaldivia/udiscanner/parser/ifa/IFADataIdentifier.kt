package io.github.erickvaldivia.udiscanner.parser.ifa

internal enum class IFADataIdentifier(

    val code: String,

    val fixedLength: Int? = null

) {

    DEVICE_IDENTIFIER(
        code = "9N"
    ),

    EXPIRATION_DATE(
        code = "D",
        fixedLength = 6
    ),

    MANUFACTURING_DATE(
        code = "16D",
        fixedLength = 8
    ),

    LOT_NUMBER(
        code = "1T"
    ),

    SERIAL_NUMBER(
        code = "S"
    ),

    QUANTITY(
        code = "Q"
    );

    companion object {

        private val values =
            entries.sortedByDescending {
                it.code.length
            }

        fun match(
            text: String
        ): IFADataIdentifier? =
            values.firstOrNull {
                text.startsWith(it.code)
            }

    }

}