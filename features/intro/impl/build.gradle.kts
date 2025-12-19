plugins {
    alias(libs.plugins.voyager.feature.impl)
}

android {
    namespace = "io.mishka.voyager.intro.impl"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.intro.api)
        }
    }
}
