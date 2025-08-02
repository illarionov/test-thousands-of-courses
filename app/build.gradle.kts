plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.example.thcources.gradle.android.hilt.ksp")
}

android {
    namespace = "com.example.thcources"
    compileSdk = libs.versions.compileSdk.map { it.toInt() }.get()

    defaultConfig {
        applicationId = "com.example.thcourses"
        minSdk = libs.versions.minSdk.map { it.toInt() }.get()
        targetSdk = libs.versions.targetSdk.map { it.toInt() }.get()
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val apiUrl = providers
            .environmentVariable("TSCOURCES_API_URL")
            .getOrElse("http://127.0.0.1:8888/")
        buildConfigField("String", "TSCOURCES_API_URL", "\"$apiUrl\"")
    }

    signingConfigs {
        val testStoreFile = providers
            .environmentVariable("SIGN_STORE")
            .orNull
            ?.let(::File) ?: rootProject.file("test_keystore.jks")
        val testStorePassword = providers
            .environmentVariable("SIGN_STORE_PASSWORD")
            .getOrElse("teststore")

        create("debugKey") {
            keyAlias = "android"
            keyPassword = "android"
            storeFile = testStoreFile
            storePassword = testStorePassword
        }
        create("releaseKey") {
            keyAlias = providers.environmentVariable("SIGN_KEY_ALIAS").getOrElse("testkey")
            keyPassword = providers.environmentVariable("SIGN_KEY_PASSWORD").getOrElse("testkey")
            storeFile = testStoreFile
            storePassword = testStorePassword
        }
    }

    buildTypes {
        debug {
            signingConfig = signingConfigs.getByName("debugKey")
        }
        release {
            isMinifyEnabled = true
            signingConfig = signingConfigs.getByName("releaseKey")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        buildConfig = true
        viewBinding = true
    }
}

dependencies {
    implementation(projects.core.thcourcesCoreDi)
    implementation(projects.core.thcourcesCoreModel)
    implementation(projects.core.thcourcesCoreUi)

    implementation(libs.adapterdelegates)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.coil.network.okhttp)
    implementation(libs.coil.xml)
    implementation(libs.material)
    implementation(libs.okhttp.logging.interceptor)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
