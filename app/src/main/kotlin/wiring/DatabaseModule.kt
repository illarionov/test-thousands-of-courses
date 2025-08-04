package com.example.thcourses.wiring

import android.content.Context
import androidx.room.Room
import androidx.sqlite.SQLiteDriver
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.example.thcourses.ThcoursesDatabase
import com.example.thcourses.core.di.IoCoroutineDispatcherContext
import com.example.thcourses.like.CourseLikeDao
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    private const val DATABASE_NAME = "db"

    @Provides
    fun providesCourseLikeDao(database: ThcoursesDatabase): CourseLikeDao = database.courseLikeDao()

    @Provides
    @Singleton
    fun providesDatabase(
        @ApplicationContext context: Context,
        @IoCoroutineDispatcherContext queryCoroutineContext: CoroutineContext,
        sqliteDriver: SQLiteDriver
    ): ThcoursesDatabase {
        return Room.databaseBuilder(context, ThcoursesDatabase::class.java, DATABASE_NAME)
            .fallbackToDestructiveMigrationOnDowngrade(dropAllTables = true)
            .setDriver(sqliteDriver)
            .setQueryCoroutineContext(queryCoroutineContext)
            .build()
    }

    @Provides
    @Reusable
    fun providesSqliteDriver(): SQLiteDriver = BundledSQLiteDriver()
}
