plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.voyager.secrets)
    alias(libs.plugins.voyager.ktor)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.supabase.api)

            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.supabase.coil)
            implementation(libs.supabase.postgrest)
            implementation(libs.supabase.storage)
            implementation(libs.supabase.functions)
            implementation(libs.kermit)
        }
    }
}
