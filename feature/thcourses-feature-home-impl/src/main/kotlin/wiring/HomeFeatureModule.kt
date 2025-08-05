package com.example.thcourses.feature.home.wiring

import com.example.thcourses.feature.home.domain.GetCoursesUseCase
import com.example.thcourses.feature.home.domain.GetCoursesUseCaseImpl
import com.example.thcourses.feature.home.domain.SetCourseFavoriteUseCase
import com.example.thcourses.feature.home.domain.SetCourseFavoriteUseCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
internal object HomeFeatureModule {

    @Module
    @InstallIn(ViewModelComponent::class)
    internal interface HomeFeatureModuleBinds {
        @Binds
        @ViewModelScoped
        fun bindsGetCoursesUseCase(impl: GetCoursesUseCaseImpl): GetCoursesUseCase

        @Binds
        @ViewModelScoped
        fun bindsSetCourseFavoriteUseCase(impl: SetCourseFavoriteUseCaseImpl): SetCourseFavoriteUseCase
    }
}
