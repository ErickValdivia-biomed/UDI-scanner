package io.github.erickvaldivia.udiscanner.parser.gs1

/**
 * GS1 Application Identifiers currently supported by the library.
 */
internal enum class GS1ApplicationIdentifier(

    val code: String,

    /**
     * Maximum value length.
     */
    val valueLength: Int,

    /**
     * True if the value terminates with FNC1 (or end of barcode).
     */
    val variableLength: Boolean

) {

    SERIAL_SHIPPING_CONTAINER_CODE(
        "00",
        18,
        false
    ),

    DEVICE_IDENTIFIER(
        "01",
        14,
        false
    ),

    LOT_NUMBER(
        "10",
        20,
        true
    ),

    MANUFACTURING_DATE(
        "11",
        6,
        false
    ),

    EXPIRATION_DATE(
        "17",
        6,
        false
    ),

    SERIAL_NUMBER(
        "21",
        20,
        true
    ),
    QUANTITY(
        "30",
        8,
        true
    ),

    ADDITIONAL_PRODUCT_IDENTIFICATION(
        "240",
        30,
        true
    ),

    CUSTOMER_PART_NUMBER(
        "241",
        30,
        true
    ),

    SECONDARY_SERIAL_NUMBER(
        "250",
        30,
        true
    ),

    UNKNOWN(
        "",
        0,
        true
    );



    companion object {

        private val lookup =
            entries.associateBy { it.code }

        fun fromCode(
            code: String
        ): GS1ApplicationIdentifier? =
            lookup[code]

    }

}