plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
    alias(libs.plugins.mokkery)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.onboarding.api)

            implementation(projects.core.repositories.userPreferences.api)
            implementation(projects.core.repositories.vibes.api)
            implementation(projects.core.storage.settings)
            implementation(projects.features.navigation.api)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
