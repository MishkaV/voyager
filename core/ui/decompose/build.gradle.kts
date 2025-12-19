
plugins {
    alias(libs.plugins.voyager.library.multiplatform.compose)
    alias(libs.plugins.voyager.decompose)
}

android {
    namespace = "io.mishkav.voyager.core.ui.decompose"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(compose.animation)
            api(compose.ui)
        }
    }
}
