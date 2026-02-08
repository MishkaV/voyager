
plugins {
    alias(libs.plugins.voyager.library.multiplatform.compose)
    alias(libs.plugins.voyager.decompose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.ui.theme)
        }
    }
}
