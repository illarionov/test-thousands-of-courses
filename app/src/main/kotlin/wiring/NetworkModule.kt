package com.example.thcourses.wiring

import com.example.thcourses.BuildConfig
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val OKHTTP_CACHE_SUBDIR = "thcourses"

//    @Provides
//    @Singleton
//    internal fun providesthcoursesNetworkDataSource(
//        @thcoursesClient okhttpClient: dagger.Lazy<@JvmSuppressWildcards OkHttpClient>,
//        @ComputationCoroutineDispatcherContext computationDispatcher: CoroutineContext,
//        @thcoursesClient baseUrl: String,
//    ): thcoursesNetworkDataSource {
//        val lazyCallFactory = Call.Factory { request: Request ->
//            okhttpClient.get().newCall(request)
//        }
//
//        return Coffee1706NetworkDataSource(
//            callFactory = lazyCallFactory,
//            baseUrl = baseUrl,
//            computationDispatcherContext = computationDispatcher,
//        )
//    }

    /**
     * Okhhtp клиент для API
     */
    @Provides
    @Singleton
    @thcoursesClient
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
    @thcoursesClient
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
    annotation class thcoursesClient

    @Qualifier
    annotation class LoggingInterceptor

}
