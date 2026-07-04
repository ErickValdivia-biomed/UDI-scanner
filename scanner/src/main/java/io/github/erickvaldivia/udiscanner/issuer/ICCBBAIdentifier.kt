package io.github.erickvaldivia.udiscanner.issuer

import io.github.erickvaldivia.udiscanner.model.Issuer

internal class ICCBBAIdentifier : IssuerIdentifier {

    override val issuer = Issuer.ICCBBA

    override fun identify(barcode: String): Boolean {

        return barcode.startsWith("=")
                || barcode.startsWith("&")
                || barcode.startsWith("%")

    }

}