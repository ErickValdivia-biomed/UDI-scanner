package io.github.erickvaldivia.udiscanner.parser.ifa

import io.github.erickvaldivia.udiscanner.exception.ScannerException

/**
 * Thrown when an IFA barcode
 * cannot be parsed.
 */
internal class IFAParsingException(

    message: String

) : ScannerException(message)