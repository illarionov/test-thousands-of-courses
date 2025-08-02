package com.example.thcources.wiring

import com.example.thcources.BuildConfig
import com.example.thcources.core.di.ComputationCoroutineDispatcherContext
import dagger.Module
import dagger.Provides
import dagger.Reusable
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.ConnectionPool
import okhttp3.Dispatcher
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Qualifier
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration.Companion.minutes
import kotlin.time.Duration.Companion.seconds

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val OKHTTP_CACHE_SUBDIR = "thcources"

//    @Provides
//    @Singleton
//    internal fun providesThcourcesNetworkDataSource(
//        @ThcourcesClient okhttpClient: dagger.Lazy<@JvmSuppressWildcards OkHttpClient>,
//        @ComputationCoroutineDispatcherContext computationDispatcher: CoroutineContext,
//        @ThcourcesClient baseUrl: String,
//    ): ThcourcesNetworkDataSource {
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
    @ThcourcesClient
    fun provideThcourcesOkhttpClient(
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
    @ThcourcesClient
    fun providesThcourcesBaseUrl(): String = BuildConfig.TSCOURCES_API_URL

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
    annotation class ThcourcesClient

    @Qualifier
    annotation class LoggingInterceptor

}
