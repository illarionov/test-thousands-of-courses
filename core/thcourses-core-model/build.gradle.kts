plugins {
    id("com.example.thcourses.gradle.android.library")
}

android {
    namespace = "com.example.thcourses.core.model"
}

dependencies {
    api(libs.eithernet)
    api(libs.kotlinx.datetime)
}
