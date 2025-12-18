plugins {
    alias(libs.plugins.voyager.library.multiplatform)
}

android {
    namespace = "io.mishka.voyager.${__moduleName}.api"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            // Add your dependencies here
        }
<#if needTest>
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
</#if>
    }
}
