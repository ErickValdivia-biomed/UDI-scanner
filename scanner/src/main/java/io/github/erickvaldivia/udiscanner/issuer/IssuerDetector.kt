package io.github.erickvaldivia.udiscanner.issuer

import io.github.erickvaldivia.udiscanner.model.IdentifiedBarcode
import io.github.erickvaldivia.udiscanner.model.Issuer
import io.github.erickvaldivia.udiscanner.recognition.RecognitionResult

internal class IssuerDetector {

    private val identifiers = listOf(

        GS1Identifier(),
        HIBCCIdentifier(),
        ICCBBAIdentifier(),
        IFAIdentifier()

    )

    fun identify(
        recognition: RecognitionResult
    ): IdentifiedBarcode {

        val issuer = identifiers.firstOrNull {

            it.identify(recognition.text)

        }?.issuer ?: Issuer.UNKNOWN

        return IdentifiedBarcode(

            rawBarcode = recognition.text,

            carrier = recognition.carrier,

            issuer = issuer

        )

    }

}