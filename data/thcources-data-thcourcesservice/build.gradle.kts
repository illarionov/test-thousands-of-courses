plugins {
    id("com.example.thcources.gradle.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.thcources.data.serviceapi"
}

dependencies {
    api(projects.core.coffee1706CoreModel)
    api(libs.okhttp)

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)
}
