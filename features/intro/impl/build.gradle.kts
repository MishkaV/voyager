plugins {
    alias(libs.plugins.voyager.feature.impl)
}

android {
    namespace = "io.mishka.voyager.intro.impl"
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.coil.gif)
        }
        commonMain.dependencies {
            api(projects.features.intro.api)

            implementation(compose.components.resources)
            implementation(projects.features.navigation.api)
        }
    }
}
