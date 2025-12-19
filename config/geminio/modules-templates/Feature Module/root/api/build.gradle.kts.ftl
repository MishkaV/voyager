plugins {
    alias(libs.plugins.voyager.library.multiplatform)
    alias(libs.plugins.voyager.decompose)
<#if isMultiScreenNavigation>
    alias(libs.plugins.voyager.kotlin.serialization)
</#if>
}

android {
    namespace = "io.mishka.voyager.${__moduleName}.api"
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.ui.decompose)
        }
<#if needTest>
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
</#if>
    }
}