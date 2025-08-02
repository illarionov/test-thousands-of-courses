plugins {
    id("com.example.thcources.gradle.android.library")
}

android {
    namespace = "com.example.thcources.core.ui"
    androidResources.enable = true
}

dependencies {
    api(projects.core.thcourcesCoreModel)
    api(libs.material)
}
