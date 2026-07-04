package io.github.erickvaldivia.udiscanner.camera

import android.content.Context
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.lifecycle.LifecycleOwner
import io.github.erickvaldivia.udiscanner.recognition.RecognitionListener
import io.github.erickvaldivia.udiscanner.recognition.Recognizer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
/**
 * Manages CameraX and forwards frames to the recognition engine.
 */
internal class CameraManager(

    private val previewView: PreviewView,
    private val lifecycleOwner: LifecycleOwner,
    private val recognizer: Recognizer,
    private val listener: RecognitionListener

) {

    private val analysisExecutor: ExecutorService =
        Executors.newSingleThreadExecutor()

    private var cameraProvider: ProcessCameraProvider? = null
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>

    private val cameraSelector =
        CameraSelector.DEFAULT_BACK_CAMERA

    private val coroutineScope =
        CoroutineScope(
            SupervisorJob() + Dispatchers.Default
        )

    fun start() {

        cameraProviderFuture =
            ProcessCameraProvider.getInstance(previewView.context)

        cameraProviderFuture.addListener(

            {

                cameraProvider = cameraProviderFuture.get()

                bindCamera()

            },

            ContextCompat.getMainExecutor(previewView.context)

        )

    }
    private fun bindCamera() {

        val provider = cameraProvider ?: return

        provider.unbindAll()

        val preview = Preview.Builder().build()

        preview.surfaceProvider = previewView.surfaceProvider

        val imageAnalysis = ImageAnalysis.Builder()
            .setBackpressureStrategy(
                ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST
            )
            .build()

        imageAnalysis.setAnalyzer(
            analysisExecutor
        ) { imageProxy ->

            val mediaImage = imageProxy.image

            if (mediaImage == null) {
                imageProxy.close()
                return@setAnalyzer
            }

            val inputImage = InputImage.fromMediaImage(
                mediaImage,
                imageProxy.imageInfo.rotationDegrees
            )

            coroutineScope.launch {

                try {

                    val results = recognizer.recognize(inputImage)

                    listener.onRecognitionComplete(results)

                } finally {

                    imageProxy.close()

                }

            }

        }

        provider.bindToLifecycle(
            lifecycleOwner,
            cameraSelector,
            preview,
            imageAnalysis
        )

    }
    fun stop() {

        cameraProvider?.unbindAll()

    }

    fun close() {

        stop()

        analysisExecutor.shutdown()

        coroutineScope.cancel()

    }

}