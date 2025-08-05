package com.example.thcourses.feature.home.domain

import com.example.thcourses.core.di.ComputationCoroutineDispatcherContext
import com.example.thcourses.core.model.Course
import com.example.thcourses.core.model.CourseId
import com.example.thcourses.data.repository.courses.api.ThcourcesRepository
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal interface GetCourseUseCase {
    fun getCourse(id: CourseId): Flow<ApiResult<Course, Unit>>
}

internal class GetCourseUseCaseImpl @Inject constructor(
    private val repository: ThcourcesRepository,
    @param:ComputationCoroutineDispatcherContext private val computationDispatcherContext: CoroutineContext,
) : GetCourseUseCase {
    override fun getCourse(id: CourseId): Flow<ApiResult<Course, Unit>> {
        return repository.getCourse(id)
    }
}
