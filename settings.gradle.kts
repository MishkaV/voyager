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
    ":entry:app",
    ":entry:app-android",

    // Common
    ":common:orchestrator:api",
    ":common:orchestrator:impl",

    // Core
    ":core:debug",
    ":core:repositories:base",
    ":core:repositories:countries:api",
    ":core:repositories:countries:impl",
    ":core:repositories:paging",
    ":core:repositories:user-preferences:api",
    ":core:repositories:user-preferences:impl",
    ":core:repositories:user-stats:api",
    ":core:repositories:user-stats:impl",
    ":core:repositories:vibes:api",
    ":core:repositories:vibes:impl",
    ":core:storage:database",
    ":core:storage:settings",
    ":core:supabase:api",
    ":core:supabase:impl",
    ":core:ui:decompose",
    ":core:ui:lifecycle",
    ":core:ui:theme",
    ":core:ui:uikit",
    ":core:utils:context",
    ":core:utils:permissions",
    ":core:utils:room",
    ":core:utils:supabase",
    ":core:utils:time:api",
    ":core:utils:time:impl",

    // Features
    ":features:auth:api",
    ":features:auth:impl",
    ":features:details:api",
    ":features:details:impl",
    ":features:home:api",
    ":features:home:impl",
    ":features:intro:api",
    ":features:intro:impl",
    ":features:location:api",
    ":features:location:impl",
    ":features:main:api",
    ":features:main:impl",
    ":features:navigation:api",
    ":features:navigation:impl",
    ":features:onboarding:api",
    ":features:onboarding:impl",
    ":features:profile:api",
    ":features:profile:impl",
    ":features:search:api",
    ":features:search:impl",
)