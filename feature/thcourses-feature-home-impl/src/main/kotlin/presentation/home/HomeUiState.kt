package com.example.thcourses.feature.home.presentation.home

import com.example.thcourses.core.model.CourseSortOrder
import com.example.thcourses.core.ui.internationalization.message.LocalizedMessage
import com.example.thcourses.feature.home.presentation.shared.model.CourseListItem

internal sealed class HomeUiState {
    data object Loading : HomeUiState()
    data class Success(
        val courses: List<CourseListItem>,
        val sortOrder: CourseSortOrder,
    ) : HomeUiState()
    data class Error(val message: LocalizedMessage) : HomeUiState()
}
