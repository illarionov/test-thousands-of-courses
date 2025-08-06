package com.example.thcourses.feature.home.domain

import com.example.thcourses.core.di.ComputationCoroutineDispatcherContext
import com.example.thcourses.core.model.Course
import com.example.thcourses.data.repository.courses.api.ThcourcesRepository
import com.example.thcourses.data.repository.courses.api.util.mapSuccess
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal interface GetFavoriteCoursesUseCase {
    fun getFavoriteCoursesFlow(): Flow<ApiResult<List<Course>, Unit>>
}

internal class GetFavoriteCoursesUseCaseImpl @Inject constructor(
    private val repository: ThcourcesRepository,
    @param:ComputationCoroutineDispatcherContext private val computationDispatcherContext: CoroutineContext,
) : GetFavoriteCoursesUseCase {
    // XXX: большее оптимальная фильтрация была бы в репозитории, но для простоты делаем так
    override fun getFavoriteCoursesFlow(): Flow<ApiResult<List<Course>, Unit>> {
        return repository.getCourses()
            .map { coursesResult: ApiResult<List<Course>, Unit> ->
                coursesResult.mapSuccess { courses ->
                    courses.filter(Course::hasLike)
                }
            }
            .flowOn(computationDispatcherContext)
    }
}
