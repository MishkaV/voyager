plugins {
    alias(libs.plugins.voyager.feature.impl)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.navigation.api)

            implementation(projects.features.auth.api)
            implementation(projects.features.intro.api)
            implementation(projects.features.location.api)
            implementation(projects.features.main.api)
            implementation(projects.features.onboarding.api)
        }
    }
}
