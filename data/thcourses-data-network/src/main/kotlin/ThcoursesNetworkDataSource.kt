package com.example.thcourses.data.thcoursesservice

import com.example.thcourses.core.model.Course
import com.example.thcourses.core.model.CourseId
import com.slack.eithernet.ApiResult

public interface ThcoursesNetworkDataSource {
    public suspend fun getCourses(): ApiResult<List<Course>, Unit>
}

public suspend fun ThcoursesNetworkDataSource.getCourse(courseId: CourseId): ApiResult<Course, Unit> {
    val courses = getCourses()
    val newResult: ApiResult<Course, Unit> = when (courses) {
        is ApiResult.Success<List<Course>> -> {
            val course = courses.value.firstOrNull { it.id == courseId }
            if (course != null) {
                ApiResult.success(course)
            } else {
                ApiResult.httpFailure(404)
            }
        }
        is ApiResult.Failure -> { courses }
    }
    return newResult
}
