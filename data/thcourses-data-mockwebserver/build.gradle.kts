plugins {
    id("com.example.thcourses.gradle.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.thcourses.data.mockwebserver"
}

dependencies {
    implementation(libs.okhttp.mockwebserver)
}
