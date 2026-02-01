plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.datastore)
            api(libs.settings)
            api(libs.settings.coroutines)

            implementation(libs.settings.datastore)
            implementation(libs.kermit)
            implementation(projects.core.utils.context)
        }
    }
}
