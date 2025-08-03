package com.example.thcourses.wiring

import com.example.thcourses.core.di.AppMainCoroutineScope
import com.example.thcourses.core.di.ComputationCoroutineDispatcherContext
import com.example.thcourses.core.di.IoCoroutineDispatcherContext
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import jakarta.inject.Singleton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object CoroutinesModule {
    /**
     * Глобальный Coroutine Scope приложения. Main thread, supervisor job. Не используем GlobalScope.
     */
    @Provides
    @AppMainCoroutineScope
    @Singleton
    fun providesAppMainCoroutineScope(): CoroutineScope = MainScope()

    @Provides
    @IoCoroutineDispatcherContext
    fun providesIoCoroutineDispatcherContext(): CoroutineContext = Dispatchers.IO

    @Provides
    @ComputationCoroutineDispatcherContext
    fun providesComputationDispatcherContext(): CoroutineContext = Dispatchers.Default
}
