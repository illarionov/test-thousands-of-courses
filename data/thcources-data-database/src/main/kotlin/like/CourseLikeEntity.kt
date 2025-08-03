package com.example.thcources.like

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.thcources.core.model.CourseId

/**
 * Проставленные пользователем лайки. Заменяют значения, пришедшие с сервера.
 */
@Entity(
    tableName = "course_like",
)
public data class CourseLikeEntity(
    @PrimaryKey val courseId: CourseId,
    val hasLike: Boolean,
)
