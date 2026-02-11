plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.main.api)

            implementation(projects.core.repositories.countries.api)
            implementation(projects.core.ui.lifecycle)
            implementation(projects.core.utils.permissions)
            implementation(projects.features.home.api)
            implementation(projects.features.navigation.api)
            implementation(projects.features.profile.api)
            implementation(projects.features.search.api)
        }
    }
}
