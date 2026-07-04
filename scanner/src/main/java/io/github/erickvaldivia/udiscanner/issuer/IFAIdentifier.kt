package io.github.erickvaldivia.udiscanner.issuer

import io.github.erickvaldivia.udiscanner.model.Issuer

internal class IFAIdentifier : IssuerIdentifier {

    override val issuer = Issuer.IFA

    override fun identify(
        barcode: String
    ): Boolean {

        // TODO:
        // Improve detection according to the IFA specification.
        // For now, detect the UDI-DI Data Identifier.
        return barcode.startsWith("9N")

    }

}