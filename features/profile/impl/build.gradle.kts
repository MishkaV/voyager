plugins {
    alias(libs.plugins.voyager.feature.impl)
    alias(libs.plugins.voyager.compose.resources)
    alias(libs.plugins.voyager.secrets)
    alias(libs.plugins.mokkery)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.profile.api)

            implementation(libs.supabase.auth)
            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(projects.core.repositories.userStats.api)
            implementation(projects.features.main.api)
            implementation(projects.features.navigation.api)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
