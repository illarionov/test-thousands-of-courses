package com.example.thcourses.wiring

import com.example.thcourses.core.di.ComputationCoroutineDispatcherContext
import com.example.thcourses.core.di.IoCoroutineDispatcherContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {
    @Provides
    @IoCoroutineDispatcherContext
    fun providesIoCoroutineDispatcherContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @ComputationCoroutineDispatcherContext
    fun providesComputationDispatcherContext(): CoroutineContext = Dispatchers.Default
}
