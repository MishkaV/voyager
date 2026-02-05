plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.main.api)

            implementation(projects.features.home.api)
            implementation(projects.features.profile.api)
            implementation(projects.features.search.api)
        }
    }
}
