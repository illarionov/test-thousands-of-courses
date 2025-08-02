package com.example.thcources.wiring

import android.content.Context
import androidx.core.net.toUri
import com.example.thcources.data.mockwebserver.ThcourcesMockWebServer
import com.example.thcources.wiring.NetworkModule.ThcourcesClient
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
        @ThcourcesClient baseUrl: String,
    ): ThcourcesMockWebServer {
        val url = baseUrl.toUri()
        return ThcourcesMockWebServer(
            port = url.port,
            inetAddress = InetAddress.getByName(url.host),
            assertManager = context.assets,
        )
    }
}
