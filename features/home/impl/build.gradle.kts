plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.secrets)
    alias(libs.plugins.voyager.compose.resources)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.mapbox)
            implementation(libs.mapbox.compose)
            implementation(libs.mapbox.geojson)
        }
        commonMain.dependencies {
            api(projects.features.home.api)

            // Move to jetbrains material only with support LinearWavyProgressIndicator
            implementation(libs.androidx.compose.material3)
            implementation(projects.common.audioController.api)
            implementation(projects.core.repositories.countries.api)
            implementation(projects.core.repositories.countryDetails.api)
            implementation(projects.core.repositories.vibes.api)
            implementation(projects.features.navigation.api)
            implementation(projects.features.main.api)
        }
    }
}
