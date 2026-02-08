plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.common.audioController.api)
            implementation(libs.kotlinx.coroutines.core)
        }
    }
}
