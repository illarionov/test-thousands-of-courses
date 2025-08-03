plugins {
    id("com.example.thcourses.gradle.android.library")
}

android {
    namespace = "com.example.thcourses.core.ui"
    androidResources.enable = true
}

dependencies {
    api(projects.core.thcoursesCoreModel)
    api(libs.material)
}
