package io.github.erickvaldivia.udiscanner.issuer

import io.github.erickvaldivia.udiscanner.model.Issuer

internal class HIBCCIdentifier : IssuerIdentifier {

    override val issuer = Issuer.HIBCC

    override fun identify(barcode: String): Boolean {

        return barcode.startsWith("+")

    }

}