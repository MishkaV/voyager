plugins {
    alias(libs.plugins.voyager.library.multiplatform.compose)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.moko.permissions.location)
            implementation(libs.moko.permissions.compose)
        }
        commonMain.dependencies {
            implementation(libs.jetbrains.compose.runtime)
            implementation(libs.jetbrains.compose.ui)
            implementation(libs.kermit)
        }
    }
}
