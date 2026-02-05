plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.voyager.room)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.utils.room)
            api(projects.core.repositories.userPreferences.api)
            api(projects.core.repositories.userStats.api)
            api(projects.core.repositories.vibes.api)

            implementation(projects.core.utils.context)
            implementation(libs.sqlite.bundled)
        }
    }
}
