package com.example.thcourses.wiring

import android.content.Context
import androidx.core.net.toUri
import com.example.thcourses.data.mockwebserver.thcoursesMockWebServer
import com.example.thcourses.wiring.NetworkModule.thcoursesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.net.InetAddress
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
public object MockWebServerModule {
    @Provides
    @Singleton
    fun providesMockWebServer(
        @ApplicationContext context: Context,
        @thcoursesClient baseUrl: String,
    ): thcoursesMockWebServer {
        val url = baseUrl.toUri()
        return thcoursesMockWebServer(
            port = url.port,
            inetAddress = InetAddress.getByName(url.host),
            assertManager = context.assets,
        )
    }
}
