package com.example.thcourses.feature.home.presentation.course

import com.example.thcourses.core.ui.internationalization.message.LocalizedMessage
import com.example.thcourses.feature.home.presentation.course.model.CourseUiItem

internal sealed class CourseUiState {
    data object Loading : CourseUiState()
    data class Success(
        val course: CourseUiItem,
    ) : CourseUiState()
    data class Error(val message: LocalizedMessage) : CourseUiState()
}

