package io.github.erickvaldivia.udiscanner.model

/**
 * Physical barcode symbology.
 */
enum class Carrier {

    DATA_MATRIX,

    CODE_128,

    CODE_39,

    QR_CODE,

    PDF_417,

    AZTEC,

    UNKNOWN

}