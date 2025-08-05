package com.example.thcourses.feature.home.presentation.shared.mapper

import com.example.thcourses.core.model.Course
import com.example.thcourses.data.repository.courses.api.util.mapSuccess
import com.example.thcourses.feature.home.presentation.shared.model.CourseListItem
import com.slack.eithernet.ApiResult

internal fun mapCoursesResult(
    courses: ApiResult<List<Course>, Unit>,
): ApiResult<List<CourseListItem>, Unit> = courses.mapSuccess { courses: List<Course> ->
    courses.map(Course::toCourseListItem)
}

internal fun Course.toCourseListItem(): CourseListItem = CourseListItem(
    id = id,
    title = title,
    text = text,
    imageUrl = imageUrl,
    price = price,
    rate = rate,
    hasLike = hasLike,
    startDate = startDate
)
