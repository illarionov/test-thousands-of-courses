package com.example.thcources.data.thcourcesservice.mapper

import com.example.thcources.core.model.Course
import com.example.thcources.core.model.CourseId
import com.example.thcources.core.model.Rate
import com.example.thcources.data.thcourcesservice.service.CourseDto
import kotlinx.datetime.LocalDate
import java.math.BigDecimal

internal fun CourseDto.toCourse(): Course = Course(
    id = CourseId(this.id),
    title = this.title,
    text = this.text,
    price = parsePrice(this.price),
    rate = Rate(this.rate),
    hasLike = this.hasLike,
    startDate = parseDate(this.startDate),
    publishDate = parseDate(this.publishDate)
)

private fun parsePrice(price: String): BigDecimal = price.filter { it.isDigit() }.toBigDecimal()

private fun parseDate(date: String): LocalDate = LocalDate.parse(date)
