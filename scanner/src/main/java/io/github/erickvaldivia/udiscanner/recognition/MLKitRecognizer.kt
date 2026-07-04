package io.github.erickvaldivia.udiscanner.recognition

import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

/**
 * ML Kit implementation of the barcode recognizer.
 *
 * This class is responsible only for barcode recognition.
 * Camera acquisition and frame management are handled by CameraManager.
 */
internal class MLKitRecognizer : Recognizer {

    /**
     * Reusable ML Kit barcode scanner.
     */
    private val scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder()
            .setBarcodeFormats(
                Barcode.FORMAT_DATA_MATRIX,
                Barcode.FORMAT_CODE_128,
                Barcode.FORMAT_CODE_39,
                Barcode.FORMAT_PDF417,
                Barcode.FORMAT_AZTEC,
                Barcode.FORMAT_QR_CODE
            )
            .build()
    )

    /**
     * Recognizes every supported barcode contained in the image.
     */
    override suspend fun recognize(
        image: InputImage
    ): List<RecognitionResult> = suspendCancellableCoroutine { continuation ->

        scanner.process(image)

            .addOnSuccessListener { barcodes ->
                println("ML Kit found ${barcodes.size} barcode(s)")
                val results = buildList {

                    for (barcode in barcodes) {
                        println("FORMAT = ${barcode.format}")
                        println("RAW    = ${barcode.rawValue}")
                        val value = barcode.rawValue ?: continue

                        add(
                            RecognitionResult(
                                text = value,
                                carrier = BarcodeFormatMapper.map(barcode.format)
                            )
                        )
                    }
                }

                if (continuation.isActive) {
                    continuation.resume(results)
                }
            }

            .addOnFailureListener { exception ->

                if (continuation.isActive) {
                    continuation.resumeWithException(
                        RecognitionException(
                            message = "ML Kit failed to recognize barcodes.",
                            cause = exception
                        )
                    )
                }
            }
    }
}