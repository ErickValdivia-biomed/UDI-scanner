package io.github.erickvaldivia.udiscanner.recognition

/**
 * Thrown when the recognition engine fails while processing an image.
 *
 * This represents an unexpected recognition failure, not simply the
 * absence of detectable barcodes.
 */
internal class RecognitionException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause)