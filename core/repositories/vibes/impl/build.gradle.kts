plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.voyager.kotlin.serialization)
    alias(libs.plugins.voyager.ktor)
    alias(libs.plugins.metro)
    alias(libs.plugins.mokkery)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.repositories.vibes.api)

            implementation(libs.supabase.postgrest)
            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(projects.core.repositories.base)
            implementation(projects.core.supabase.api)
        }

        commonTest.dependencies {
            implementation(kotlin("test"))
            implementation(libs.kotlinx.coroutines.test)
        }
    }
}
