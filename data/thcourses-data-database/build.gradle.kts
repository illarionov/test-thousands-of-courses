plugins {
    id("com.example.thcourses.gradle.android.library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "com.example.thcourses.data.database"
}

room {
    schemaDirectory(layout.projectDirectory.dir("schemas"))
}

dependencies {
    api(projects.core.thcoursesCoreModel)

    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.sqlite.bundled)
    testImplementation(libs.room.testing)
}
