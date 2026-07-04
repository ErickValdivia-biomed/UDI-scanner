package io.github.erickvaldivia.udiscanner.parser.ifa

/**
 * One parsed ANSI MH10 data element.
 */
internal data class IFAToken(

    val identifier: IFADataIdentifier,

    val value: String

)