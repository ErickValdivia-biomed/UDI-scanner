package io.github.erickvaldivia.udiscanner.recognition

/**
 * Internal callback invoked after barcode recognition.
 */
internal fun interface RecognitionListener {

    suspend fun onRecognitionComplete(

        results: List<RecognitionResult>

    )

}