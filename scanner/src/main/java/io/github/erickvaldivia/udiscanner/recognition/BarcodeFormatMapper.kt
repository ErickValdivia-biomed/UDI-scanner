package io.github.erickvaldivia.udiscanner.recognition

import com.google.mlkit.vision.barcode.common.Barcode
import io.github.erickvaldivia.udiscanner.model.Carrier

/**
 * Converts ML Kit barcode formats into library carriers.
 */
internal object BarcodeFormatMapper {

    fun map(format: Int): Carrier {

        return when (format) {

            Barcode.FORMAT_DATA_MATRIX ->
                Carrier.DATA_MATRIX

            Barcode.FORMAT_CODE_128 ->
                Carrier.CODE_128

            Barcode.FORMAT_CODE_39 ->
                Carrier.CODE_39

            Barcode.FORMAT_QR_CODE ->
                Carrier.QR_CODE

            Barcode.FORMAT_PDF417 ->
                Carrier.PDF_417

            Barcode.FORMAT_AZTEC ->
                Carrier.AZTEC

            else ->
                Carrier.UNKNOWN
        }

    }

}