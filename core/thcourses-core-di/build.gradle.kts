plugins {
    id("com.example.thcourses.gradle.android.library")
}

android {
    namespace = "com.example.thcourses.core.di"
}

dependencies {
    implementation(libs.hilt)
}
