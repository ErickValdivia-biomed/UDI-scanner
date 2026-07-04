package io.github.erickvaldivia.udiscanner.api

import io.github.erickvaldivia.udiscanner.exception.ScannerException
import io.github.erickvaldivia.udiscanner.model.ParsedUDI

/**
 * Receives scanner results.
 */
interface ScanListener {

    /**
     * Successfully recognized and parsed a UDI.
     */
    fun onScan(
        parsedUDI: ParsedUDI
    )

    /**
     * Scanner failed.
     */
    fun onError(
        exception: ScannerException
    )

}