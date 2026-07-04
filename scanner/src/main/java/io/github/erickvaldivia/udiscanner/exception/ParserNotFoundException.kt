package io.github.erickvaldivia.udiscanner.exception

import io.github.erickvaldivia.udiscanner.model.Issuer

/**
 * Thrown when no parser has been registered
 * for a particular issuer.
 */
internal class ParserNotFoundException(
    issuer: Issuer
) : ScannerException(
    "No parser registered for issuer: $issuer"
)