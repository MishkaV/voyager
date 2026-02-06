plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.voyager.room)
    alias(libs.plugins.voyager.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.repositories.base)
            api(projects.core.repositories.paging)
            api(projects.core.utils.room)
        }
    }
}
