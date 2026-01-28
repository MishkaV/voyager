plugins {
    alias(libs.plugins.voyager.feature.impl)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.navigation.api)

            implementation(projects.features.intro.api)
        }
    }
}
