import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.voyager.application.multiplatform)
    alias(libs.plugins.voyager.coil)
    alias(libs.plugins.voyager.decompose)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.coil.gif)
        }
        commonMain.dependencies {
            api(projects.common.orchestrator.impl)
            api(projects.core.debug)
            api(projects.core.repositories.userPreferences.impl)
            api(projects.core.repositories.vibes.impl)
            api(projects.core.storage.database)
            api(projects.core.storage.settings)
            api(projects.core.supabase.impl)
            api(projects.core.utils.context)
            api(projects.core.utils.time.impl)
            api(projects.features.auth.impl)
            api(projects.features.intro.impl)
            api(projects.features.location.impl)
            api(projects.features.main.impl)
            api(projects.features.navigation.impl)
            api(projects.features.onboarding.impl)

            implementation(libs.jetbrains.compose.material3)
            implementation(libs.kermit)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

compose.desktop {
    application {
        mainClass = "io.mishkav.voyager.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "io.mishkav.voyager"
            packageVersion = "1.0.0"
        }
    }
}
