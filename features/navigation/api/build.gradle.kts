plugins {
    alias(libs.plugins.voyager.library.multiplatform.compose)
    alias(libs.plugins.voyager.decompose)
    alias(libs.plugins.voyager.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(compose.ui)

            api(projects.core.ui.decompose)
        }
    }
}
