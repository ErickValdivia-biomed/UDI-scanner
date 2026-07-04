package io.github.erickvaldivia.udiscanner.sample

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import io.github.erickvaldivia.udiscanner.api.ScanListener
import io.github.erickvaldivia.udiscanner.api.UDIScanner
import io.github.erickvaldivia.udiscanner.exception.ScannerException
import io.github.erickvaldivia.udiscanner.model.ParsedUDI

class MainActivity : AppCompatActivity(), ScanListener {

    private lateinit var previewView: PreviewView

    private lateinit var statusText: TextView

    private lateinit var resultText: TextView

    private lateinit var scanner: UDIScanner

    override fun onCreate(
        savedInstanceState: Bundle?
    ) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        previewView = findViewById(R.id.previewView)

        statusText = findViewById(R.id.statusText)

        resultText = findViewById(R.id.resultText)

        if (hasCameraPermission()) {

            initializeScanner()

        } else {

            ActivityCompat.requestPermissions(

                this,

                arrayOf(Manifest.permission.CAMERA),

                CAMERA_PERMISSION_REQUEST

            )

        }

    }

    private fun initializeScanner() {

        scanner = UDIScanner(

            previewView = previewView,

            lifecycleOwner = this,

            listener = this

        )

        scanner.start()

    }

    override fun onScan(
        parsedUDI: ParsedUDI
    ) {

        runOnUiThread {

            statusText.text = "✔ This is a UDI barcode."

            resultText.text = buildResult(
                parsedUDI
            )

        }

    }

    override fun onError(
        exception: ScannerException
    ) {

        runOnUiThread {

            statusText.text =
                "✘ This barcode is not recognized as a supported UDI."

            resultText.text =
                exception.message ?: "Unknown error."

        }

    }

    override fun onDestroy() {

        super.onDestroy()

        scanner.close()

    }

    private fun hasCameraPermission(): Boolean {

        return ContextCompat.checkSelfPermission(

            this,

            Manifest.permission.CAMERA

        ) == PackageManager.PERMISSION_GRANTED

    }

    override fun onRequestPermissionsResult(

        requestCode: Int,

        permissions: Array<String>,

        grantResults: IntArray

    ) {

        super.onRequestPermissionsResult(

            requestCode,

            permissions,

            grantResults

        )

        if (

            requestCode == CAMERA_PERMISSION_REQUEST &&
            grantResults.isNotEmpty() &&
            grantResults[0] == PackageManager.PERMISSION_GRANTED

        ) {

            initializeScanner()

        }

    }
    private fun buildResult(
        parsedUDI: ParsedUDI
    ): String {

        return buildString {

            appendLine("Issuer: ${parsedUDI.issuer}")
            appendLine("Carrier: ${parsedUDI.carrier}")
            appendLine()

            appendLine("Device Identifier")
            appendLine(parsedUDI.deviceIdentifier)

            parsedUDI.productionIdentifiers.lotNumber?.let {

                appendLine()
                appendLine("Lot")
                appendLine(it)

            }

            parsedUDI.productionIdentifiers.serialNumber?.let {

                appendLine()
                appendLine("Serial Number")
                appendLine(it)

            }

            parsedUDI.productionIdentifiers.manufacturingDate?.let {

                appendLine()
                appendLine("Manufacturing Date")
                appendLine(it)

            }

            parsedUDI.productionIdentifiers.expirationDate?.let {

                appendLine()
                appendLine("Expiration Date")
                appendLine(it)

            }

        }

    }
    companion object {

        private const val CAMERA_PERMISSION_REQUEST = 1

    }

}