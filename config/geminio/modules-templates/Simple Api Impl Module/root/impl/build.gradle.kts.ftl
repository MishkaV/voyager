plugins {
    alias(libs.plugins.voyager.library.multiplatform)
<#if needDI>
    alias(libs.plugins.metro)
</#if>
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.${__moduleName}.api)
        }
<#if needTest>
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
</#if>
    }
}
