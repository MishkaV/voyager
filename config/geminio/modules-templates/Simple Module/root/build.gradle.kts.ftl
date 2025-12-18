plugins {
    alias(libs.plugins.voyager.library.multiplatform)
<#if needDI>
    alias(libs.plugins.metro)
</#if>
}

android {
    namespace = "io.mishka.voyager.${__moduleName}"
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
