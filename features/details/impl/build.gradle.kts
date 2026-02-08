plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.details.api)

            implementation(projects.common.audioController.api)
            implementation(projects.core.repositories.countries.api)
            implementation(projects.core.repositories.countryDetails.api)
            implementation(projects.features.navigation.api)
        }
    }
}
