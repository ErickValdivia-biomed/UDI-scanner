package io.github.erickvaldivia.udiscanner.parser.gs1

/**
 * Result of reading a variable-length GS1 field.
 */
internal data class VariableLengthField(

    /**
     * Extracted field value.
     */
    val value: String,

    /**
     * Cursor position immediately after the field.
     *
     * If a Group Separator was encountered, this points to it.
     * Otherwise it points to the end of the barcode or the
     * maximum permitted field length.
     */
    val nextCursor: Int

)