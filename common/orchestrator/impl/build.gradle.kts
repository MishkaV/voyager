plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.common.orchestrator.api)

            implementation(libs.supabase.auth)
            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(projects.core.repositories.base)
        }
    }
}
