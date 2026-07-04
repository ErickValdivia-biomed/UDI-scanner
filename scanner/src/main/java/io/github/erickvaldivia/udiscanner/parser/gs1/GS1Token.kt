package io.github.erickvaldivia.udiscanner.parser.gs1

/**
 * Represents a single GS1 Application Identifier and
 * its associated value.
 */
internal data class GS1Token(

    /**
     * Parsed AI if supported by the library.
     * Null means the AI is valid but currently unsupported.
     */
    val applicationIdentifier: GS1ApplicationIdentifier,

    val value: String

) {

    /**
     * Numeric GS1 AI code.
     *
     * Useful for logging and debugging.
     */
    val code: String
        get() = applicationIdentifier.code

}