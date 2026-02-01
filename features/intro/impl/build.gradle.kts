plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.coil.gif)
        }
        commonMain.dependencies {
            api(projects.features.intro.api)

            implementation(projects.core.supabase.api)
            implementation(projects.features.navigation.api)
        }
    }
}
