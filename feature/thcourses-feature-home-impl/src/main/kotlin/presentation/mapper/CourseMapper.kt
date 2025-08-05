package com.example.thcourses.feature.home.presentation.mapper

import com.example.thcourses.core.model.Course
import com.example.thcourses.feature.home.presentation.model.CourseListItem

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
