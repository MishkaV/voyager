plugins {
    alias(libs.plugins.voyager.feature.impl)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.${__moduleName}.api)
        }
<#if needTest>
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
</#if>
    }
}
