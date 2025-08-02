package com.example.thcources.data.thcourcesservice.service

import kotlinx.serialization.Serializable

@Serializable
public data class CourseDto(
    val id: Long,
    val title: String,
    val text: String,
    val price: String,
    val rate: String,
    val hasLike: Boolean,
    val startDate: String,
    val publishDate: String,
)
