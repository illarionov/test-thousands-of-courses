package com.example.thcourses.feature.home.presentation.course.model

import com.example.thcourses.core.model.Course

internal fun Course.toCourseUiItem(): CourseUiItem = CourseUiItem(
    id = id,
    title = title,
    text = text,
    imageUrl = imageUrl,
    price = price,
    rate = rate,
    hasLike = hasLike,
    startDate = startDate,
    publishDate = publishDate,
    author = CourseAuthorUiItem(
        name = "Not implemented",
        avatarUrl = null
    )
)
