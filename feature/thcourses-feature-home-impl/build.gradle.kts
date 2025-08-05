plugins {
    id("com.example.thcourses.gradle.android.library")
    id("com.example.thcourses.gradle.android.hilt.ksp")
}

android {
    namespace = "com.example.thcourses.feature.home.impl"
    androidResources.enable = true

    buildFeatures {
        viewBinding = true
        resValues = true
    }
}

dependencies {
    api(projects.core.thcoursesCoreModel)
    implementation(projects.core.thcoursesCoreDi)
    implementation(projects.core.thcoursesCoreNavigation)
    implementation(projects.core.thcoursesCoreUi)
    implementation(projects.data.thcoursesDataRepositoryCoursesApi)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
}
