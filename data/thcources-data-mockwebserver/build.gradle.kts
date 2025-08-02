plugins {
    id("com.example.thcources.gradle.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.thcources.data.serviceapi"
}

dependencies {
    implementation(projects.core.thcourcesCoreModel)
    implementation(libs.okhttp)
    implementation(libs.okhttp.mockwebserver)

    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)
}
