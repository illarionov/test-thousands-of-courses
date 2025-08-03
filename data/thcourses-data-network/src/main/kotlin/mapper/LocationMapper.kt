package com.example.thcourses.data.thcoursesservice.mapper

import com.example.thcourses.core.model.Course
import com.example.thcourses.core.model.CourseId
import com.example.thcourses.core.model.Rate
import com.example.thcourses.data.thcoursesservice.service.CourseDto
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
