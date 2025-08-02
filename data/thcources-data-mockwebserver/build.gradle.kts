plugins {
    id("com.example.thcources.gradle.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.thcources.data.mockwebserver"
}

dependencies {
    implementation(libs.okhttp.mockwebserver)
}
