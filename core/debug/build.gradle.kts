plugins {
    alias(libs.plugins.voyager.library.multiplatform)
}

android {
    namespace = "io.mishkav.voyager.core.debug"

    buildFeatures {
        buildConfig = true
    }
}
