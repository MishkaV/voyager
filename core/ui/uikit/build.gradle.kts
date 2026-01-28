plugins {
    alias(libs.plugins.voyager.library.multiplatform.compose)
    alias(libs.plugins.voyager.coil)
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
