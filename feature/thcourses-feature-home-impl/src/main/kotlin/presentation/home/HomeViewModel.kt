@file:OptIn(ExperimentalCoroutinesApi::class)

package com.example.thcourses.feature.home.presentation.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thcourses.core.di.ComputationCoroutineDispatcherContext
import com.example.thcourses.core.model.Course
import com.example.thcourses.core.model.CourseId
import com.example.thcourses.core.model.CourseSortOrder
import com.example.thcourses.core.model.CourseSortOrder.PUBLISH_DATE
import com.example.thcourses.core.model.CourseSortOrder.UNSORTED
import com.example.thcourses.core.ui.internationalization.getCommonErrorMessage
import com.example.thcourses.feature.home.domain.GetCoursesUseCase
import com.example.thcourses.feature.home.domain.SetCourseFavoriteUseCase
import com.example.thcourses.feature.home.presentation.mapper.toCourseListItem
import com.example.thcourses.feature.home.presentation.model.CourseListItem
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
internal class HomeViewModel @Inject constructor(
    private val getCourseUseCase: GetCoursesUseCase,
    private val setFavoriteUseCase: SetCourseFavoriteUseCase,
    @param:ComputationCoroutineDispatcherContext private val computationDispatcherContext: CoroutineContext,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val sortOrderFlow: MutableStateFlow<CourseSortOrder> =
        savedStateHandle.getMutableStateFlow("sortOrder", UNSORTED)

    val uiState: StateFlow<HomeUiState> = sortOrderFlow
        .flatMapConcat { sortOrder ->
            getCourseUseCase
                .getCoursesFlow(sortOrder)
                .map(::mapCoursesResult)
                .map { coursesResult: ApiResult<List<CourseListItem>, Unit> ->
                    when (coursesResult) {
                        is ApiResult.Success<List<CourseListItem>> -> {
                            HomeUiState.Success(
                                courses = coursesResult.value,
                                sortOrder = sortOrder,
                            )
                        }

                        is ApiResult.Failure -> HomeUiState.Error(
                            message = coursesResult.getCommonErrorMessage(),
                        )
                    }
                }
                .flowOn(computationDispatcherContext)
        }
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeUiState.Loading,
        )


    fun setFavorite(courseId: CourseId, isFavorite: Boolean) {
        viewModelScope.launch {
            setFavoriteUseCase.setFavorite(courseId, isFavorite)
        }
    }

    fun toggleSortOrder() {
        sortOrderFlow.value = when (sortOrderFlow.value) {
            UNSORTED -> PUBLISH_DATE
            PUBLISH_DATE -> UNSORTED
        }
    }

    companion object {
        private fun mapCoursesResult(
            courses: ApiResult<List<Course>, Unit>,
        ): ApiResult<List<CourseListItem>, Unit> = when (courses) {
            is ApiResult.Success<List<Course>> -> {
                val listItems = courses.value.map(Course::toCourseListItem)
                ApiResult.success(listItems)
            }

            is ApiResult.Failure -> courses
        }
    }
}
