package com.example.thcourses.core.ui.internationalization.formatter

import com.example.thcourses.core.model.Rate
import java.text.NumberFormat

/**
 * Форматирование рейтинга
 */
public class RatingFormatter {
    private val rateFormat: NumberFormat = NumberFormat.getNumberInstance()

    public fun format(rate: Rate): String {
        return rateFormat.format(rate.value)
    }
}
