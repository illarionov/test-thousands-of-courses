pluginManagement {
    includeBuild("build-logic/project")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "thousands-of-courses"
include(":app")
include(":core:thcourses-core-di")
include(":core:thcourses-core-model")
include(":core:thcourses-core-navigation")
include(":core:thcourses-core-ui")
include(":data:thcourses-data-database")
include(":data:thcourses-data-mockwebserver")
include(":data:thcourses-data-network")
include(":data:thcourses-data-repository-courses-api")
include(":data:thcourses-data-repository-courses-impl")
include(":feature:thcourses-feature-account-impl")
include(":feature:thcourses-feature-auth-impl")
include(":feature:thcourses-feature-home-impl")

