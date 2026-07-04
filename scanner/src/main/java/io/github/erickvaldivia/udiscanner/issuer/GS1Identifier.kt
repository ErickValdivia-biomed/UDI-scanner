package io.github.erickvaldivia.udiscanner.issuer

import io.github.erickvaldivia.udiscanner.issuer.constants.GS1Constants
import io.github.erickvaldivia.udiscanner.model.Issuer

internal class GS1Identifier : IssuerIdentifier {

    override val issuer = Issuer.GS1

    override fun identify(barcode: String): Boolean {

        if (barcode.isBlank()) {
            return false
        }

        if (barcode.first() == GS1Constants.GROUP_SEPARATOR) {
            return true
        }

        return GS1Constants.COMMON_AIS.any {
            barcode.startsWith(it)
        }

    }

}