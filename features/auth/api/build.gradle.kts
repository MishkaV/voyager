plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.voyager.decompose)
    alias(libs.plugins.voyager.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.ui.decompose)
        }
    }
}
