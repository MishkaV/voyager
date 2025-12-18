plugins {
    alias(libs.plugins.voyager.feature.impl)
}

android {
    namespace = "io.mishkav.voyager.features.navigation.impl"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.navigation.api)
        }
    }
}
