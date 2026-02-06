plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.search.api)

            implementation(libs.androidx.paging.compose)
            implementation(projects.core.repositories.countries.api)
            implementation(projects.features.main.api)
            implementation(projects.features.navigation.api)
        }
    }
}
