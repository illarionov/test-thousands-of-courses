package com.example.thcourses.feature.home.presentation.model

import com.example.thcourses.core.model.CourseId
import com.example.thcourses.core.model.Rate
import kotlinx.datetime.LocalDate
import java.math.BigDecimal

internal data class CourseListItem(
    val id: CourseId,
    val title: String,
    val text: String,
    val imageUrl: String?,
    val price: BigDecimal,
    val rate: Rate,
    val hasLike: Boolean,
    val startDate: LocalDate,
)
