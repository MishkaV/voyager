plugins {
    alias(libs.plugins.voyager.library.multiplatform.compose)
}

android {
    namespace = "io.mishkav.voyager.core.ui.theme"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(compose.runtime)
            api(compose.foundation)
            api(compose.material3)
            api(compose.ui)

            implementation(compose.components.resources)
        }
    }
}
