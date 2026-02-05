plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
    alias(libs.plugins.voyager.secrets)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.profile.api)

            implementation(projects.features.main.api)
        }
    }
}
