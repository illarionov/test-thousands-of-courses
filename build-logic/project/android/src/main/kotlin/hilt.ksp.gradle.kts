package com.example.thcources.gradle.android

plugins {
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

ksp {
    // https://dagger.dev/dev-guide/compiler-options.html
    listOf(
        "fastInit",
        "ignoreProvisionKeyWildcards",
        "strictMultibindingValidation",
        "useBindingGraphFix",
    ).forEach {
        arg("-Adagger.$it", "ENABLED")
    }
}

dependencies {
    ksp(versionCatalog.findLibrary("hilt-compiler").get())
    add("implementation", versionCatalog.findLibrary("hilt").get())
}
