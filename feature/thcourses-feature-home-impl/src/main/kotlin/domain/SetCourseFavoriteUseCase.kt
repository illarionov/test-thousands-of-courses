package com.example.thcourses.feature.home.domain

import com.example.thcourses.core.model.CourseId
import com.example.thcourses.data.repository.courses.api.ThcourcesRepository
import javax.inject.Inject

internal interface SetCourseFavoriteUseCase {
    suspend fun setFavorite(courseId: CourseId, isFavorite: Boolean): Unit
}

internal class SetCourseFavoriteUseCaseImpl @Inject constructor (
    private val repository: ThcourcesRepository,
) : SetCourseFavoriteUseCase {
    override suspend fun setFavorite(courseId: CourseId, isFavorite: Boolean) {
        repository.setFavorite(courseId, isFavorite)
    }
}
