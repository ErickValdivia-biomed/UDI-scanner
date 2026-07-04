package io.github.erickvaldivia.udiscanner.api

import androidx.lifecycle.LifecycleOwner
import androidx.camera.view.PreviewView
import io.github.erickvaldivia.udiscanner.camera.CameraManager
import io.github.erickvaldivia.udiscanner.exception.ScannerException
import io.github.erickvaldivia.udiscanner.issuer.IssuerDetector
import io.github.erickvaldivia.udiscanner.normalization.BarcodeNormalizer
import io.github.erickvaldivia.udiscanner.parser.ParserManager
import io.github.erickvaldivia.udiscanner.recognition.MLKitRecognizer
import io.github.erickvaldivia.udiscanner.recognition.RecognitionListener

/**
 * Public entry point of the library.
 */
class UDIScanner(

    previewView: PreviewView,

    lifecycleOwner: LifecycleOwner,

    private val listener: ScanListener

) {

    private val recognizer = MLKitRecognizer()

    private val issuerDetector = IssuerDetector()
    private val parserManager = ParserManager()
    private val barcodeNormalizer =
        BarcodeNormalizer()

    private val cameraManager = CameraManager(

        previewView = previewView,

        lifecycleOwner = lifecycleOwner,

        recognizer = recognizer,

        listener = RecognitionListener { recognitions ->
//
            for (recognition in recognitions) {


                try {

                    val normalized =
                        barcodeNormalizer.normalize(recognition)



                    val identified =
                        issuerDetector.identify(normalized)



                    try {

                        val parsed =
                            parserManager.parse(identified)



                        listener.onScan(parsed)

                    } catch (exception: Exception) {

                        println("UDI detected but parsing failed.")
                        println("Reason: ${exception.message}")

                        listener.onError(

                            ScannerException(

                                buildString {

                                    appendLine("UDI detected")
                                    appendLine()

                                    appendLine("Issuer : ${identified.issuer}")
                                    appendLine("Carrier: ${identified.carrier}")
                                    appendLine()

                                    appendLine(
                                        "The barcode belongs to a supported UDI issuer,"
                                    )

                                    appendLine(
                                        "but some identifiers could not be interpreted."
                                    )

                                    if (exception.message != null) {

                                        appendLine()
                                        appendLine("Details:")
                                        append(exception.message)

                                    }

                                }

                            )

                        )

                    }

                } catch (exception: Exception) {

                    exception.printStackTrace()

                    listener.onError(

                        ScannerException(

                            exception.message
                                ?: "Unknown recognition error."

                        )

                    )

                }

            }

        }

    )
    /**
     * Parser manager.
     */


    fun start() =
        cameraManager.start()

    fun stop() =
        cameraManager.stop()

    fun close() =
        cameraManager.close()

}