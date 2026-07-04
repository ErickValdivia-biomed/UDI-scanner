package io.github.erickvaldivia.udiscanner.recognition

import io.github.erickvaldivia.udiscanner.model.Carrier

/**
 * Result produced directly by the recognition engine.
 */
internal data class RecognitionResult(

    val text: String,

    val carrier: Carrier

)