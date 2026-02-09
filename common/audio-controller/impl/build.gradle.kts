plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.voyager.compose.resources)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.common.audioController.api)
            implementation(libs.kotlinx.coroutines.core)
            implementation(projects.core.utils.context)
            implementation(project.dependencies.platform(libs.supabase.bom))
            implementation(libs.supabase.storage)
            implementation(libs.kermit)
        }

        androidMain.dependencies {
            implementation(libs.androidx.media3.exoplayer)
            implementation(libs.androidx.media3.session)
            implementation(libs.kotlinx.coroutines.android)
            implementation(libs.kotlinx.coroutines.guava)
        }
    }
}
