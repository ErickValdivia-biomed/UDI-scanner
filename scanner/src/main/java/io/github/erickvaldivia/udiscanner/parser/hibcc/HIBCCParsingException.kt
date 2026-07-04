package io.github.erickvaldivia.udiscanner.parser.hibcc

import io.github.erickvaldivia.udiscanner.exception.ScannerException

/**
 * Thrown when a HIBCC barcode cannot be parsed.
 */
internal class HIBCCParsingException(
    message: String
) : ScannerException(message)