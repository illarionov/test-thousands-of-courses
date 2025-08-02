package com.example.thcources.data.thcourcesservice.service

import com.slack.eithernet.ApiResult
import retrofit2.http.GET

internal interface CoursesService {
    @GET("/v1/courses")
    suspend fun getLocations(): ApiResult<List<CourseDto>, Unit>
}
