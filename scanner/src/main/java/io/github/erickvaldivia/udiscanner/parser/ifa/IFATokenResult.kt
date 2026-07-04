package io.github.erickvaldivia.udiscanner.parser.ifa

/**
 * Result of reading one ANSI MH10 data element.
 */
internal data class IFATokenResult(

    val token: IFAToken,

    val nextCursor: Int

)