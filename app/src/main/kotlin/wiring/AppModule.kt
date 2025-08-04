package com.example.thcourses.wiring

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Module
    @InstallIn(SingletonComponent::class)
    interface AppModuleBinds {

    }
}
