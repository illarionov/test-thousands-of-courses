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
    schemaDirectory("$projectDir/schemas")
}

dependencies {
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)
    implementation(libs.sqlite.bundled)
    testImplementation(libs.room.testing)
}
