plugins {
    id("com.example.thcourses.gradle.android.library")
}

android {
    namespace = "com.example.thcourses.data.repository.courses.impl"
}

dependencies {
    api(projects.data.thcoursesDataRepositoryCoursesApi)

    implementation(projects.data.thcoursesDataNetwork)
    implementation(projects.data.thcoursesDataDatabase)
}
