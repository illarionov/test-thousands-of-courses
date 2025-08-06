package com.example.thcourses.data.thcoursesservice.service

import com.slack.eithernet.ApiResult
import retrofit2.http.GET

internal interface CoursesService {
    @GET("/v1/courses")
    suspend fun getCourses(): ApiResult<CoursesDto, Unit>
}
