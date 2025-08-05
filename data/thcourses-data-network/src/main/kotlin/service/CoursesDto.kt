package com.example.thcourses.data.thcoursesservice.service

import kotlinx.serialization.Serializable

@Serializable
internal data class CoursesDto(
    val courses: List<CourseDto>
)
