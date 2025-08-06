package com.example.thcourses.feature.home.presentation.course

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thcourses.core.model.CourseId
import com.example.thcourses.core.ui.internationalization.getCommonErrorMessage
import com.example.thcourses.data.repository.courses.api.util.mapSuccess
import com.example.thcourses.feature.home.domain.GetCourseUseCase
import com.example.thcourses.feature.home.domain.SetCourseFavoriteUseCase
import com.example.thcourses.feature.home.presentation.course.model.CourseUiItem
import com.example.thcourses.feature.home.presentation.course.model.toCourseUiItem
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
internal class CourseViewModel @Inject constructor(
    getCourseUseCase: GetCourseUseCase,
    private val setFavoriteUseCase: SetCourseFavoriteUseCase,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val courseId = CourseId(savedStateHandle["courseId"] ?: error("No course id"))

    val uiState: StateFlow<CourseUiState> = getCourseUseCase.getCourse(courseId)
        .map {
            it.mapSuccess { course -> course.toCourseUiItem() }
        }
        .map { coursesResult: ApiResult<CourseUiItem, Unit> ->
            when (coursesResult) {
                is ApiResult.Success<CourseUiItem> -> {
                    CourseUiState.Success(
                        course = coursesResult.value,
                    )
                }

                is ApiResult.Failure -> CourseUiState.Error(
                    message = coursesResult.getCommonErrorMessage(),
                )
            }
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = CourseUiState.Loading,
        )

    fun toggleFavorite() {
        val state: CourseUiState = uiState.value
        val isFavorite = if (state is CourseUiState.Success) {
            state.course.hasLike
        } else {
            return
        }
        viewModelScope.launch {
            setFavoriteUseCase.setFavorite(courseId, !isFavorite)
        }
    }
}
