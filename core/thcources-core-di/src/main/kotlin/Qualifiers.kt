package com.example.thcources.core.di

import javax.inject.Qualifier

/**
 * Глобальный Coroutine Scope приложения. Main thread, supervisor job. Не используем GlobalScope.
 */
@Qualifier
public annotation class AppMainCoroutineScope

/**
 * Coroutine Dispatcher для IO-операций
 */
@Qualifier
public annotation class IoCoroutineDispatcherContext

/**
 * Coroutine Dispatcher для операций на CPU.
 */
@Qualifier
public annotation class ComputationCoroutineDispatcherContext
