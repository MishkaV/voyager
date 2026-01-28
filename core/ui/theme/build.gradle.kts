plugins {
    alias(libs.plugins.voyager.library.multiplatform.compose)
    alias(libs.plugins.voyager.compose.resources)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.jetbrains.compose.runtime)
            api(libs.jetbrains.compose.foundation)
            api(libs.jetbrains.compose.material3)
            api(libs.jetbrains.compose.ui)
        }
    }
}
