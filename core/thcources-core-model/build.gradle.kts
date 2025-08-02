plugins {
    id("com.example.thcources.gradle.android.library")
}

android {
    namespace = "com.example.thcources.core.model"
}

dependencies {
    api(libs.eithernet)
    api(libs.kotlinx.datetime)
}
