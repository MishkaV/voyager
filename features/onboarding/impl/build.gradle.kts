plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.onboarding.api)

            implementation(projects.features.navigation.api)
        }
    }
}
