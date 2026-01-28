plugins {
    alias(libs.plugins.voyager.library.multiplatform)
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
