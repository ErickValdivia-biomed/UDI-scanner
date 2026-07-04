package io.github.erickvaldivia.udiscanner.issuer.constants

/**
 * Constants used for GS1 identification.
 *
 * NOTE:
 * These are only the Application Identifiers required for
 * issuer detection. The complete AI catalogue belongs to
 * the GS1 parser.
 */
internal object GS1Constants {

    /**
     * Application Identifiers commonly found in UDI barcodes.
     */
    val COMMON_AIS = setOf(
        "00",
        "01",
        "10",
        "11",
        "17",
        "21"
    )

    /**
     * ASCII Group Separator (FNC1)
     */
    const val GROUP_SEPARATOR = '\u001D'

}