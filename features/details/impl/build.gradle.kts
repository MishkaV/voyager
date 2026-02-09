plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.details.api)

            // Move to jetbrains material only with support LinearWavyProgressIndicator
            implementation(libs.androidx.compose.material3)
            implementation(libs.markdown)
            implementation(libs.jetbrains.compose.material3)
            implementation(projects.common.audioController.api)
            implementation(projects.core.repositories.countries.api)
            implementation(projects.core.repositories.countryDetails.api)
            implementation(projects.features.navigation.api)
        }
    }
}
