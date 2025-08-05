package com.example.thcourses.core.ui.internationalization.formatter

import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Currency

/**
 * Форматтер для цены в виде "200 руб"
 */
public class PriceFormatter {
    private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance("RUB")
        minimumFractionDigits = 0
        maximumFractionDigits = 2
    }

    public fun format(price: BigDecimal): String {
        return currencyFormat.format(price)
    }
}
