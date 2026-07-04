package io.github.erickvaldivia.udiscanner.recognition

import com.google.mlkit.vision.common.InputImage

internal interface Recognizer {

    suspend fun recognize(
        image: InputImage
    ): List<RecognitionResult>

}