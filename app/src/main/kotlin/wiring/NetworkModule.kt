package com.example.thcourses.wiring

import com.example.thcourses.BuildConfig
import com.example.thcourses.core.di.ComputationCoroutineDispatcherContext
import com.example.thcourses.data.thcoursesservice.ThcoursesNetworkDataSource
import com.example.thcourses.data.thcoursesservice.ThcoursesNetworkDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Call
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val OKHTTP_CACHE_SUBDIR = "thcourses"

    /**
     * Network Data Source для сервиса API
     */
    @Provides
    @Reusable
    internal fun providesThcoursesNetworkDataSource(
        @ThcoursesClient okhttpClient: dagger.Lazy<@JvmSuppressWildcards OkHttpClient>,
        @ComputationCoroutineDispatcherContext computationDispatcher: CoroutineContext,
        @ThcoursesClient baseUrl: String,
    ): ThcoursesNetworkDataSource {
        val lazyCallFactory = Call.Factory { request: Request ->
            okhttpClient.get().newCall(request)
        }

        return ThcoursesNetworkDataSourceImpl(
            callFactory = lazyCallFactory,
            baseUrl = baseUrl,
            computationDispatcherContext = computationDispatcher,
        )
    }

    /**
     * Okhhtp клиент для API
     */
    @Provides
    @Singleton
    @ThcoursesClient
    fun providethcoursesOkhttpClient(
        @RootOkhttpClient rootOkhttpClient: dagger.Lazy<@JvmSuppressWildcards OkHttpClient>,
        cache: dagger.Lazy<@JvmSuppressWildcards Cache>,
        @LoggingInterceptor loggingInterceptor: Interceptor?,
    ): OkHttpClient {
        return rootOkhttpClient.get().newBuilder()
            .cache(cache.get())
            .apply {
                if (loggingInterceptor != null) {
                    addInterceptor(loggingInterceptor)
                }
            }
            .build()
    }

    @Provides
    @ThcoursesClient
    fun providesthcoursesBaseUrl(): String = BuildConfig.TSCOURCES_API_URL

    /**
     * Базовый okhttp клиент, от которого создаются клиенты под конкретные сервисы.
     *
     * Используется для Coil и для сервера API.
     */
    @Provides
    @Singleton
    @RootOkhttpClient
    fun providesRootOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }

    @Provides
    @LoggingInterceptor
    @Reusable
    fun providesLoggingInterceptor(): Interceptor? {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().apply {
                setLevel(HttpLoggingInterceptor.Level.BODY)
            }
        } else {
            null
        }
    }

    @Qualifier
    annotation class RootOkhttpClient

    @Qualifier
    annotation class ThcoursesClient

    @Qualifier
    annotation class LoggingInterceptor

}
