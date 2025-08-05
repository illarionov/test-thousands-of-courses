package com.example.thcourses

import android.app.Application
import android.os.Build
import android.os.StrictMode
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.disk.directory
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import com.example.thcourses.data.mockwebserver.thcoursesMockWebServer
import com.example.thcourses.wiring.NetworkModule.RootOkhttpClient
import dagger.hilt.android.HiltAndroidApp
import okhttp3.OkHttpClient
import javax.inject.Inject
import javax.inject.Provider

@HiltAndroidApp
class ThcoursesApplication : SingletonImageLoader.Factory,  Application() {
    @set:Inject
    var coilImageLoaderFactory: Provider<CoilImageLoaderFactory>? = null

    @set:Inject
    var mockWebServer: Provider<thcoursesMockWebServer>? = null

    override fun onCreate() {
        super.onCreate()
        setupStrictMode()
        startMockWebServer()
    }

    override fun newImageLoader(context: PlatformContext): ImageLoader {
        return checkNotNull(coilImageLoaderFactory) {
            "Image loader factory not injected"
        }.get().newImageLoader(context)
    }

    private fun startMockWebServer() {
        checkNotNull(mockWebServer) { "Mock web server not injected" }.get().start()
    }

    private companion object {
        private fun setupStrictMode() {
            if (!BuildConfig.DEBUG) return

            StrictMode.setThreadPolicy(
                StrictMode.ThreadPolicy.Builder().detectAll().build(),
            )
            StrictMode.setVmPolicy(
                StrictMode.VmPolicy.Builder().apply {
                    detectActivityLeaks()
                    detectNonSdkApiUsage()
                    detectContentUriWithoutPermission()
                    detectFileUriExposure()
                    detectLeakedClosableObjects()
                    detectLeakedRegistrationObjects()
                    detectLeakedSqlLiteObjects()

                    if (Build.VERSION.SDK_INT >= 29) {
                        detectImplicitDirectBoot()
                        detectCredentialProtectedWhileLocked()
                    }
                    if (Build.VERSION.SDK_INT >= 31) {
                        detectUnsafeIntentLaunch()
                        detectIncorrectContextUse()
                    }
                    if (Build.VERSION.SDK_INT >= 36) {
                        detectBlockedBackgroundActivityLaunch()
                    }
                }.build(),
            )
        }
    }

    class CoilImageLoaderFactory @Inject constructor(
        @param:RootOkhttpClient val rootOkhttpClient: Provider<OkHttpClient>,
    ) : SingletonImageLoader.Factory {
        override fun newImageLoader(context: PlatformContext): ImageLoader {
            val coilOkhttpClient = rootOkhttpClient.get().newBuilder().build()
            return ImageLoader.Builder(context)
                .components {
                    add(
                        OkHttpNetworkFetcherFactory(callFactory = coilOkhttpClient),
                    )
                }
                .memoryCache {
                    MemoryCache.Builder()
                        .maxSizePercent(context, MAX_MEMORY_CACHE_SIZE_PERCENT)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(context.cacheDir.resolve("image_cache"))
                        .maxSizeBytes(MAX_DISK_CACHE_SIZE_BYTES)
                        .build()
                }
                .build()
        }

        companion object {
            const val MAX_MEMORY_CACHE_SIZE_PERCENT = 0.10
            const val MAX_DISK_CACHE_SIZE_BYTES = 10_000_000L
        }
    }
}
