package com.example.thcourses.feature.home.presentation.favorites

import com.example.thcourses.core.ui.internationalization.message.LocalizedMessage
import com.example.thcourses.feature.home.presentation.shared.model.CourseListItem

internal sealed class FavoritesUiState {
    data object Loading : FavoritesUiState()
    data class Success(
        val courses: List<CourseListItem>,
    ) : FavoritesUiState()
    data class Error(val message: LocalizedMessage) : FavoritesUiState()
}
