package com.example.thcourses.feature.home.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.thcourses.core.di.ComputationCoroutineDispatcherContext
import com.example.thcourses.core.model.CourseId
import com.example.thcourses.core.ui.internationalization.getCommonErrorMessage
import com.example.thcourses.feature.home.domain.GetFavoriteCoursesUseCase
import com.example.thcourses.feature.home.domain.SetCourseFavoriteUseCase
import com.example.thcourses.feature.home.presentation.home.HomeUiState
import com.example.thcourses.feature.home.presentation.shared.mapper.mapCoursesResult
import com.example.thcourses.feature.home.presentation.shared.model.CourseListItem
import com.slack.eithernet.ApiResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
internal class FavoritesViewModel @Inject constructor(
    private val getFavoriteCourseUseCase: GetFavoriteCoursesUseCase,
    private val setFavoriteUseCase: SetCourseFavoriteUseCase,
    @param:ComputationCoroutineDispatcherContext private val computationDispatcherContext: CoroutineContext,
) : ViewModel() {
    val uiState: StateFlow<FavoritesUiState> = getFavoriteCourseUseCase.getFavoriteCoursesFlow()
        .map(::mapCoursesResult)
        .map { coursesResult: ApiResult<List<CourseListItem>, Unit> ->
            when (coursesResult) {
                is ApiResult.Success<List<CourseListItem>> -> {
                    FavoritesUiState.Success(
                        courses = coursesResult.value,
                    )
                }

                is ApiResult.Failure -> FavoritesUiState.Error(
                    message = coursesResult.getCommonErrorMessage(),
                )
            }
        }
        .flowOn(computationDispatcherContext)
        .stateIn(
            viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = FavoritesUiState.Loading,
        )

    fun removeFromFavorite(courseId: CourseId) {
        viewModelScope.launch {
            setFavoriteUseCase.setFavorite(courseId, false)
        }
    }
}
