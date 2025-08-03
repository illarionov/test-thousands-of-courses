package com.example.thcourses.data.mockwebserver

import android.content.res.AssetManager
import mockwebserver3.Dispatcher
import mockwebserver3.MockResponse
import mockwebserver3.MockWebServer
import mockwebserver3.RecordedRequest
import okhttp3.HttpUrl
import okio.Buffer
import okio.BufferedSource
import okio.IOException
import okio.buffer
import okio.source
import java.net.InetAddress

public class thcoursesMockWebServer(
    private val port: Int,
    private val inetAddress: InetAddress? = null,
    private val assertManager: AssetManager,
) : AutoCloseable {
    private val dispatcher: Dispatcher = object : Dispatcher() {
        override fun dispatch(request: RecordedRequest): MockResponse {
            return when (request.url.encodedPath) {
                "/v1/courses" -> if (request.method == "GET") {
                    assertManager.readMockJsonResponse("courses.json")
                } else {
                    MockResponse.Builder()
                        .code(405)
                        .status("Method not allowed")
                        .build()
                }
                else -> MockResponse.Builder().code(404).build()
            }
        }
    }
    private val server: MockWebServer = MockWebServer().apply {
        dispatcher = this@thcoursesMockWebServer.dispatcher
    }

    public fun getBaseUrl(): HttpUrl = server.url("/")

    public fun start() {
        server.start(inetAddress ?: InetAddress.getLocalHost(), port)
    }

    override fun close() {
        server.close()
    }

    public companion object {
        private const val ASSERT_BASE_PATH = "mockwebserver"

        private fun AssetManager.readMockJsonResponse(path: String): MockResponse {
            return try {
                val body = open("$ASSERT_BASE_PATH/$path")
                    .source()
                    .buffer()
                    .use { source: BufferedSource ->
                        Buffer().also { source.readAll(it) }
                    }
                MockResponse.Builder()
                    .code(200)
                    .setHeader("Content-type", "application/json; charset=UTF8")
                    .body(body)
                    .build()
            } catch (ex: IOException) {
                MockResponse.Builder()
                    .code(500)
                    .status("Error ${ex.toString()}")
                    .build()
            }
        }
    }
}
