package io.github.erickvaldivia.udiscanner.issuer

import io.github.erickvaldivia.udiscanner.model.Issuer

internal interface IssuerIdentifier {

    fun identify(barcode: String): Boolean

    val issuer: Issuer

}