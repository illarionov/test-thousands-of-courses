plugins {
    id("com.example.thcources.gradle.android.library")
}

android {
    namespace = "com.example.thcources.core.di"
}

dependencies {
    implementation(libs.hilt)
}
