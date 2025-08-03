plugins {
    id("com.example.thcources.gradle.android.library")
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
}

android {
    namespace = "com.example.thcources.data.database"
}

room {
    schemaDirectory(layout.projectDirectory.dir("schemas"))
}

dependencies {
    api(projects.core.thcourcesCoreModel)

    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.sqlite.bundled)
    testImplementation(libs.room.testing)
}
