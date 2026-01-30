plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.credentials)
            implementation(libs.androidx.credentials.play.services)
            implementation(libs.android.identity)
        }
        commonMain.dependencies {
            api(projects.features.auth.api)

            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.supabase.compose.auth)
        }
    }
}
