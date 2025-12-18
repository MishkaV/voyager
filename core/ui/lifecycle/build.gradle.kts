plugins {
    alias(libs.plugins.voyager.library.multiplatform.compose)
    alias(libs.plugins.voyager.decompose)
}

android {
    namespace = "io.mishkav.voyager.core.ui.lifecycle"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kotlinx.coroutines.core)
            api(compose.runtime)

            implementation(libs.androidx.annotaion)
        }
    }
}
