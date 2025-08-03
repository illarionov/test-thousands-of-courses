plugins {
    id("com.example.thcourses.gradle.android.library")
}

android {
    namespace = "com.example.thcourses.data.repository.courses.api"
}

dependencies {
    api(projects.core.thcoursesCoreModel)
}
