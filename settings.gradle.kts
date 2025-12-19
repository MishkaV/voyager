rootProject.name = "Voyager"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}


include(
    // Entry
    ":app",

    // Core
    ":core:debug",
    ":core:ui:decompose",
    ":core:ui:lifecycle",
    ":core:ui:theme",
    ":core:ui:uikit",

    // Features
    ":features:intro:api",
    ":features:intro:impl",
    ":features:navigation:api",
    ":features:navigation:impl",
)