plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.voyager.decompose)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.ui.decompose)
        }
    }
}
