package com.example.thcourses.core.model

import kotlinx.datetime.LocalDate
import java.math.BigDecimal

public data class Course(
    val id: CourseId,
    val title: String,
    val text: String,
    val price: BigDecimal,
    val rate: Rate,
    val hasLike: Boolean,
    val startDate: LocalDate,
    val publishDate: LocalDate
)

@JvmInline
public value class CourseId(public val value: Long)

