plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.voyager.room)
}
kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.androidx.room.runtime)
        }
    }
}
