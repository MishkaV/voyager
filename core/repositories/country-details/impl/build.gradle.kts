plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.voyager.kotlin.serialization)
    alias(libs.plugins.voyager.ktor)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.repositories.countryDetails.api)

            implementation(libs.supabase.postgrest)
            implementation(libs.supabase.functions)
            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(projects.core.repositories.base)
            implementation(projects.core.supabase.api)
        }
    }
}
