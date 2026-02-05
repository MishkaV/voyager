plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
    alias(libs.plugins.voyager.secrets)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.profile.api)

            implementation(libs.supabase.auth)
            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(projects.features.main.api)
            implementation(projects.features.navigation.api)
        }
    }
}
