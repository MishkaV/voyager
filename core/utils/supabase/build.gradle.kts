plugins {
    alias(libs.plugins.voyager.library.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {

            api(project.dependencies.platform(libs.supabase.bom))
            api(libs.supabase.coil)
        }
    }
}
