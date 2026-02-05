plugins {
    alias(libs.plugins.voyager.feature.impl)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.search.api)
        }
    }
}
