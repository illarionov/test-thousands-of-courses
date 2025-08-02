plugins {
    id("com.example.thcources.gradle.android.library")
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.thcources.data.network"
}

dependencies {
    api(projects.core.thcourcesCoreModel)

    implementation(platform(libs.coroutines.bom))
    implementation(libs.coroutines.core)
    implementation(libs.okhttp)
    implementation(libs.retrofit)
    implementation(libs.retrofit.kotlinx.serialization)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.eithernet)
    implementation(libs.eithernet.retrofit)
}
