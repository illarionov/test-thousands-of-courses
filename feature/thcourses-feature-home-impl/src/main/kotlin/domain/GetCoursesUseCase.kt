package com.example.thcourses.feature.home.domain

import com.example.thcourses.core.di.ComputationCoroutineDispatcherContext
import com.example.thcourses.core.model.Course
import com.example.thcourses.core.model.CourseSortOrder
import com.example.thcourses.core.model.CourseSortOrder.PUBLISH_DATE
import com.example.thcourses.core.model.CourseSortOrder.UNSORTED
import com.example.thcourses.data.repository.courses.api.ThcourcesRepository
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

internal interface GetCoursesUseCase {
    fun getCoursesFlow(sortOrder: CourseSortOrder): Flow<ApiResult<List<Course>, Unit>>
}

internal class GetCoursesUseCaseImpl @Inject constructor(
    private val repository: ThcourcesRepository,
    @param:ComputationCoroutineDispatcherContext private val computationDispatcherContext: CoroutineContext,
) : GetCoursesUseCase {
    override fun getCoursesFlow(sortOrder: CourseSortOrder): Flow<ApiResult<List<Course>, Unit>> {
        return when (sortOrder) {
            UNSORTED -> repository.getCourses()
            PUBLISH_DATE -> repository.getCourses()
                .map { result: ApiResult<List<Course>, Unit> ->
                    when (result) {
                        is ApiResult.Success<List<Course>> -> {
                            val newList: List<Course> = result.value.sortedBy(Course::publishDate)
                            ApiResult.success(newList)
                        }

                        is ApiResult.Failure -> result
                    }
                }
        }
    }

}
