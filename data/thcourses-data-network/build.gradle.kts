plugins {
    id("com.example.thcourses.gradle.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.thcourses.data.network"
}

dependencies {
    api(projects.core.thcoursesCoreModel)

    implementation(platform(libs.coroutines.bom))
    implementation(libs.coroutines.core)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.eithernet.retrofit)
}
