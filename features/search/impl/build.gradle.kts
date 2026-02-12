plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
    alias(libs.plugins.mokkery)
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

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
