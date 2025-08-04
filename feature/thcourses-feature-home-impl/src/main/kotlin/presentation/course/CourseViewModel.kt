package com.example.thcourses.feature.home.presentation.course

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.thcourses.core.model.CourseId

internal class CourseViewModel(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {
    val courseId = CourseId(savedStateHandle["courseId"] ?: error("No course id"))
}
