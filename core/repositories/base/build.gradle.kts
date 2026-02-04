plugins {
    alias(libs.plugins.voyager.library.multiplatform)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.kermit)

            implementation(libs.kotlinx.coroutines.core)
            implementation(projects.core.utils.time.api)
        }
    }
}
