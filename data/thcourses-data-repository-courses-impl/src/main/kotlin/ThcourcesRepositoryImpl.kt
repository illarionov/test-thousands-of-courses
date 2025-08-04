package com.example.thcourses.data.repository.courses.impl

import com.example.thcourses.core.model.Course
import com.example.thcourses.core.model.CourseId
import com.example.thcourses.data.repository.courses.api.ThcourcesRepository
import com.example.thcourses.data.thcoursesservice.ThcoursesNetworkDataSource
import com.example.thcourses.like.CourseLikeDao
import com.example.thcourses.like.CourseLikeEntity
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlin.coroutines.CoroutineContext

public class ThcourcesRepositoryImpl(
    private val network: ThcoursesNetworkDataSource,
    private val localLikesDao: CourseLikeDao,
    private val computationDispatcherContext: CoroutineContext,
) : ThcourcesRepository {
    override fun getCourses(): Flow<ApiResult<List<Course>, Unit>> {
        val networkFlow = flow { emit(network.getCourses()) }
        val likesFlow: Flow<ApiResult<List<CourseLikeEntity>, Unit>> = localLikesDao.getLikesFlow()
            .map { ApiResult.success(it) as ApiResult<List<CourseLikeEntity>, Unit> }
            .catch { error ->
                val result = ApiResult.apiFailure<Unit>().withTags(mapOf(Exception::class to error))
                emit(result)
            }

        return combine(
            networkFlow,
            likesFlow,
        ) { courses: ApiResult<List<Course>, Unit>, likes: ApiResult<List<CourseLikeEntity>, Unit> ->
            when {
                courses is ApiResult.Success<List<Course>> && likes is ApiResult.Success<List<CourseLikeEntity>> -> {
                    val newCourses: List<Course> = setLikes(courses.value, likes.value)
                    ApiResult.success(newCourses)
                }

                courses is ApiResult.Failure -> courses
                else -> likes as ApiResult.Failure
            }
        }
            .flowOn(computationDispatcherContext)
    }

    override suspend fun setLike(courseId: CourseId, hasLike: Boolean) {
        localLikesDao.setLike(CourseLikeEntity(courseId = courseId, hasLike = hasLike))
    }

    private companion object {
        private fun setLikes(courses: List<Course>, likes: List<CourseLikeEntity>): List<Course> {
            val likesMap = likes.associate { it.courseId to it.hasLike }
            return courses.map {
                val newHasLike = likesMap[it.id]
                if (newHasLike != null && newHasLike != it.hasLike) {
                    it.copy(hasLike = newHasLike)
                } else {
                    it
                }
            }
        }
    }
}
