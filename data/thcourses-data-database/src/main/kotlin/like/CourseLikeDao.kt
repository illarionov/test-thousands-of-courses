package com.example.thcourses.like

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
public interface CourseLikeDao {
    @Query("SELECT * FROM course_like")
    public fun getLikesFlow(): Flow<List<CourseLikeEntity>>

    @Upsert
    public suspend fun setLike(entity: CourseLikeEntity)

    @Query("DELETE FROM course_like")
    public fun clean()
}
