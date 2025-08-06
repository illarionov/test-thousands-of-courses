package com.example.thcourses.core.di

import javax.inject.Qualifier

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
