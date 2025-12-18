plugins {
    alias(libs.plugins.voyager.library.multiplatform.compose)
    alias(libs.plugins.voyager.coil)
}

android {
    namespace = "io.mishkav.voyager.core.ui.uikit"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.compose.placeholder)
            api(projects.core.ui.theme)

            implementation(libs.kermit)
        }
    }
}
