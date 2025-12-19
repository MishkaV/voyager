import com.android.build.api.dsl.ApkSigningConfig
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.voyager.application.multiplatform)
    alias(libs.plugins.voyager.coil)
    alias(libs.plugins.voyager.decompose)
    alias(libs.plugins.voyager.secrets)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.jetbrains.compose.hotreload)
    alias(libs.plugins.metro)
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.androidx.activity.compose)
            implementation(libs.metro.android)
        }
        commonMain.dependencies {
            implementation(compose.material3)

            implementation(libs.kermit)

            implementation(projects.core.debug)
            implementation(projects.features.intro.impl)
            implementation(projects.features.navigation.impl)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
        }
    }
}

android {
    namespace = "io.mishkav.voyager"

    defaultConfig {
        applicationId = "io.mishkav.voyager"
        androidResources.localeFilters += "en"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        signingConfigs {
            newSignConfig("sign")
        }
    }
    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("sign")
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
            )
            signingConfig = signingConfigs.getByName("sign")
        }
    }
}

fun NamedDomainObjectContainer<out ApkSigningConfig>.newSignConfig(
    name: String,
) {
    val keystorePropertiesFile = rootProject.file("keystore.properties")
    val keystoreProperties = Properties()
    keystoreProperties.load(FileInputStream(keystorePropertiesFile))

    create(name) {
        keyAlias = keystoreProperties["keyAlias"] as String
        keyPassword = keystoreProperties["keyPassword"] as String
        storeFile = file(keystoreProperties["storeFile"] as String)
        storePassword = keystoreProperties["storePassword"] as String
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
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
