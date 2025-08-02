package com.example.thcources.data.thcourcesservice

import com.example.thcources.core.model.Course
import com.slack.eithernet.ApiResult

public interface ThcoursesDataSource {
    public suspend fun getCourses(): ApiResult<List<Course>, Unit>
}
