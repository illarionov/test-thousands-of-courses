package com.example.thcourses.wiring

import com.example.thcourses.core.di.ComputationCoroutineDispatcherContext
import com.example.thcourses.data.repository.courses.api.ThcourcesRepository
import com.example.thcourses.data.repository.courses.impl.ThcourcesRepositoryImpl
import com.example.thcourses.data.thcoursesservice.ThcoursesNetworkDataSource
import com.example.thcourses.like.CourseLikeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(ViewModelComponent::class)
object ThcoursesRepositoryModule {
    @Provides
    @ViewModelScoped
    fun bindsCoursesRepository(
        network: ThcoursesNetworkDataSource,
        localLikesDao: CourseLikeDao,
        @ComputationCoroutineDispatcherContext computationDispatcherContext: CoroutineContext,
    ):  ThcourcesRepository = ThcourcesRepositoryImpl(network, localLikesDao, computationDispatcherContext)

}
