plugins {
    `kotlin-dsl`
}

group = "com.example.thcourses.gradle.android"

dependencies {
    implementation(libs.agp.plugin.api)
    implementation(libs.hilt.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.ksp.plugin)
    runtimeOnly(libs.agp.plugin)
}
