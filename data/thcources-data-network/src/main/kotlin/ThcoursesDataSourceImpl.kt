package com.example.thcources.data.thcourcesservice

import com.example.thcources.core.model.Course
import com.example.thcources.data.thcourcesservice.mapper.toCourse
import com.example.thcources.data.thcourcesservice.service.CourseDto
import com.example.thcources.data.thcourcesservice.service.CoursesService
import com.slack.eithernet.ApiResult
import com.slack.eithernet.integration.retrofit.ApiResultCallAdapterFactory
import com.slack.eithernet.integration.retrofit.ApiResultConverterFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.Call
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import kotlin.coroutines.CoroutineContext

private val applicationJsonMediaType = "application/json; charset=UTF8".toMediaType()

public fun ThcoursesDataSource(
    callFactory: Call.Factory,
    baseUrl: String = "https://localhost:8888",
    computationDispatcherContext: CoroutineContext = Dispatchers.Default,
): ThcoursesDataSource {
    val json = Json
    val retrofit = Retrofit.Builder().apply {
        callFactory(callFactory)
        baseUrl(baseUrl)
        addCallAdapterFactory(ApiResultCallAdapterFactory)
        addConverterFactory(ApiResultConverterFactory)
        addConverterFactory(json.asConverterFactory(applicationJsonMediaType))
    }.build()

    val service = retrofit.create<CoursesService>()
    return ThcoursesDataSourceImpl(service, computationDispatcherContext)
}

private class ThcoursesDataSourceImpl(
    private val service: CoursesService,
    private val computationDispatcherContext: CoroutineContext,
) : ThcoursesDataSource {
    override suspend fun getCourses(): ApiResult<List<Course>, Unit> {
        val coursesDto: ApiResult<List<CourseDto>, Unit> = service.getLocations()
        return when (coursesDto) {
            is ApiResult.Success<List<CourseDto>> -> {
                withContext(computationDispatcherContext) {
                    try {
                        val courses: List<Course> = coursesDto.value.map(CourseDto::toCourse)
                        ApiResult.success(courses)
                    } catch (ex: RuntimeException) {
                        coroutineContext.ensureActive()
                        ApiResult.apiFailure<Unit>().withTags(mapOf(Exception::class to ex))
                    }
                }
            }
            is ApiResult.Failure<Unit> -> coursesDto
        }
    }
}
