package com.example.thcourses.data.thcoursesservice

import com.example.thcourses.core.model.Course
import com.slack.eithernet.ApiResult

public interface ThcoursesNetworkDataSource {
    public suspend fun getCourses(): ApiResult<List<Course>, Unit>
}
