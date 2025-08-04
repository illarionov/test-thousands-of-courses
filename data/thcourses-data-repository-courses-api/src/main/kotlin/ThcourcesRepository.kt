package com.example.thcourses.data.repository.courses.api

import com.example.thcourses.core.model.Course
import com.example.thcourses.core.model.CourseId
import com.slack.eithernet.ApiResult
import kotlinx.coroutines.flow.Flow

public interface ThcourcesRepository {
    public fun getCourses(): Flow<ApiResult<List<Course>, Unit>>
    public suspend fun setLike(courseId: CourseId, hasLike: Boolean)
}
