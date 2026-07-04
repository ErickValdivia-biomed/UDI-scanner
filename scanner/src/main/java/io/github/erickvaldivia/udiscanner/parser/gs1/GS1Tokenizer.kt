package io.github.erickvaldivia.udiscanner.parser.gs1

internal class GS1Tokenizer {

    fun tokenize(
        barcode: String
    ): List<GS1Token> {
        val normalizedBarcode =
            barcode.trimStart(GS1Constants.GROUP_SEPARATOR)

        val tokens = mutableListOf<GS1Token>()

        var cursor = 0

        while (cursor < normalizedBarcode.length) {

            if (cursor + GS1Constants.AI_LENGTH > normalizedBarcode.length) {
                throw GS1ParsingException(
                    "Unexpected end of barcode while reading Application Identifier."
                )
            }

            val aiCode = normalizedBarcode.substring(
                cursor,
                cursor + GS1Constants.AI_LENGTH
            )
            println("AI = '$aiCode'")

            val ai = GS1ApplicationIdentifier.fromCode(aiCode)
                ?: throw GS1ParsingException(
                    "Unsupported Application Identifier: $aiCode"
                )

            cursor += GS1Constants.AI_LENGTH

            val value = if (ai.variableLength) {

                val end = findVariableFieldEnd(
                    normalizedBarcode,
                    cursor,
                    ai.valueLength
                )

                val result = normalizedBarcode.substring(cursor, end)

                cursor = end

                if (
                    cursor < normalizedBarcode.length &&
                    normalizedBarcode[cursor] == GS1Constants.GROUP_SEPARATOR
                ) {
                    cursor++
                }

                result

            } else {

                if (cursor + ai.valueLength > normalizedBarcode.length) {
                    throw GS1ParsingException(
                        "Unexpected end of barcode while reading AI ${ai.code}."
                    )
                }

                val result = normalizedBarcode.substring(
                    cursor,
                    cursor + ai.valueLength
                )

                cursor += ai.valueLength

                result

            }
            cursor = skipGroupSeparator(
                normalizedBarcode,
                cursor
            )

            tokens += GS1Token(
                applicationIdentifier = ai,
                value = value
            )

        }

        return tokens

    }
    /**
     * Reads the Application Identifier starting at the current cursor.
     */
    private fun readApplicationIdentifier(
        barcode: String,
        cursor: Int
    ): GS1ApplicationIdentifier {

        if (cursor + GS1Constants.AI_LENGTH > barcode.length) {
            throw GS1ParsingException(
                "Unexpected end of barcode while reading Application Identifier."
            )
        }

        val code = barcode.substring(
            cursor,
            cursor + GS1Constants.AI_LENGTH
        )

        return GS1ApplicationIdentifier.fromCode(code)
            ?: throw GS1ParsingException(
                "Unsupported Application Identifier: $code"
            )

    }
    /**
     * Reads the value of a fixed-length Application Identifier.
     */
    private fun readFixedLengthValue(
        barcode: String,
        cursor: Int,
        length: Int
    ): String {

        if (cursor + length > barcode.length) {
            throw GS1ParsingException(
                "Unexpected end of barcode while reading fixed-length value."
            )
        }

        return barcode.substring(
            cursor,
            cursor + length
        )

    }
    /**
     * Reads the value of a variable-length Application Identifier.
     */
    private fun readVariableLengthValue(
        barcode: String,
        cursor: Int,
        maximumLength: Int
    ): VariableLengthField {

        val limit = minOf(
            barcode.length,
            cursor + maximumLength
        )

        var end = cursor

        while (end < limit) {

            if (barcode[end] == GS1Constants.GROUP_SEPARATOR) {
                break
            }

            end++

        }

        return VariableLengthField(

            value = barcode.substring(cursor, end),

            nextCursor = end

        )

    }
    /**
     * Advances the cursor past a Group Separator if one is present.
     */
    private fun skipGroupSeparator(
        barcode: String,
        cursor: Int
    ): Int {

        if (
            cursor < barcode.length &&
            barcode[cursor] == GS1Constants.GROUP_SEPARATOR
        ) {
            return cursor + 1
        }

        return cursor

    }
    private fun findVariableFieldEnd(

        barcode: String,

        start: Int,

        maximumLength: Int

    ): Int {

        val limit = minOf(
            barcode.length,
            start + maximumLength
        )

        var index = start

        while (index < limit) {

            if (barcode[index] == GS1Constants.GROUP_SEPARATOR) {
                return index
            }

            index++

        }

        return limit

    }

}