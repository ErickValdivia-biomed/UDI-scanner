package io.github.erickvaldivia.udiscanner.normalization

import io.github.erickvaldivia.udiscanner.recognition.RecognitionResult

/**
 * Normalizes barcode payloads into a canonical representation.
 *
 * Responsibilities:
 * - Remove ISO/IEC 15434 envelope if present.
 * - Convert DIN 16598 printable notation.
 * - Preserve Group Separators (GS) as '\u001D'.
 */
internal class BarcodeNormalizer {

    fun normalize(
        recognition: RecognitionResult
    ): RecognitionResult {

        var payload = recognition.text

        // --------------------------------------------------
        // ISO/IEC 15434
        // --------------------------------------------------

        if (payload.startsWith("[)>")) {

            // Remove "[)>"
            payload = payload.removePrefix("[)>")

            // Remove Record Separator (RS)
            if (
                payload.startsWith('\u001E')
            ) {
                payload = payload.substring(1)
            }

            // Remove format header
            if (
                payload.startsWith("06")
            ) {

                payload = payload.substring(2)

            } else if (

                payload.isNotEmpty() &&
                payload[0] == '\u0006'

            ) {

                payload = payload.substring(1)

            }

            // Remove Group Separator after header
            if (
                payload.startsWith('\u001D')
            ) {

                payload = payload.substring(1)

            }

            // Remove trailer RS EOT
            if (

                payload.endsWith(
                    "${'\u001E'}${'\u0004'}"
                )

            ) {

                payload = payload.dropLast(2)

            }

        }

        // --------------------------------------------------
        // DIN 16598 printable representation
        // --------------------------------------------------

        payload = payload.removePrefix(".")

        payload = payload.replace(
            '^',
            '\u001D'
        )

        println("NORMALIZED: $payload")

        return recognition.copy(
            text = payload
        )

    }

}