package io.github.erickvaldivia.udiscanner.parser.gs1

import java.time.LocalDate

/**
 * Parses GS1 YYMMDD date values.
 */
internal object GS1DateParser {

    /**
     * Converts a GS1 date (YYMMDD) into a LocalDate.
     *
     * @throws GS1ParsingException if the value is not a valid GS1 date.
     */
    fun parse(value: String): LocalDate {

        if (value.length != 6) {
            throw GS1ParsingException(
                "Invalid GS1 date length: '$value'. Expected YYMMDD."
            )
        }

        val year = value.substring(0, 2).toIntOrNull()
            ?: throw GS1ParsingException("Invalid year in GS1 date: '$value'.")

        val month = value.substring(2, 4).toIntOrNull()
            ?: throw GS1ParsingException("Invalid month in GS1 date: '$value'.")

        val day = value.substring(4, 6).toIntOrNull()
            ?: throw GS1ParsingException("Invalid day in GS1 date: '$value'.")

        val fullYear = if (year <= 49) {
            2000 + year
        } else {
            1900 + year
        }

        return try {
            LocalDate.of(fullYear, month, day)
        } catch (exception: Exception) {
            throw GS1ParsingException(
                "Invalid GS1 date: '$value'."
            )
        }

    }

}