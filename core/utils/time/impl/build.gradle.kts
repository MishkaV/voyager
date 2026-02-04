plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.utils.time.api)

            implementation(libs.kermit)
            implementation(projects.core.storage.settings)
        }
    }
}
