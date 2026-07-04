package io.github.erickvaldivia.udiscanner.parser.gs1

import io.github.erickvaldivia.udiscanner.exception.ScannerException

/**
 * Thrown when a GS1 barcode cannot be tokenized.
 */
internal class GS1ParsingException(
    message: String
) : ScannerException(message)