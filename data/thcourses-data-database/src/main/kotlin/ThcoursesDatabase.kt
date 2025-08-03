package com.example.thcourses

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.thcourses.like.CourseLikeDao
import com.example.thcourses.like.CourseLikeEntity

@Database(
    version = 1,
    entities = [
        CourseLikeEntity::class,
    ]
)
public abstract class ThcoursesDatabase : RoomDatabase() {
    public abstract fun courseLikeDao(): CourseLikeDao
}
