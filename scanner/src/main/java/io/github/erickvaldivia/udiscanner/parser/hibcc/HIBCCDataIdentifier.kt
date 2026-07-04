package io.github.erickvaldivia.udiscanner.parser.hibcc

/**
 * HIBCC data structures recognized by the tokenizer.
 */
internal enum class HIBCCDataIdentifier {

    /**
     * Primary Data Structure.
     */
    PRIMARY,

    /**
     * Secondary Data Structure.
     */
    SECONDARY,

    /**
     * Supplemental Serial Number field.
     */
    SERIAL_NUMBER,

    /**
     * Supplemental Manufacturing Date field.
     */
    MANUFACTURING_DATE,

    /**
     * Supplemental Expiration Date field.
     */
    EXPIRATION_DATE,

    /**
     * Supplemental Quantity field.
     */
    QUANTITY

}