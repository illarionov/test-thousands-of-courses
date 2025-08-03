package com.example.thcources

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.thcources.like.CourseLikeDao
import com.example.thcources.like.CourseLikeEntity

@Database(
    version = 1,
    entities = [
        CourseLikeEntity::class,
    ]
)
public abstract class ThcoursesDatabase : RoomDatabase() {
    public abstract fun courseLikeDao(): CourseLikeDao
}
