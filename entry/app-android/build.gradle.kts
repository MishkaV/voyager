import com.android.build.api.dsl.ApkSigningConfig
import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.voyager.application)
    alias(libs.plugins.voyager.decompose)
    alias(libs.plugins.android.secrets)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.jetbrains.compose.compiler)
    alias(libs.plugins.metro)
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
    buildFeatures {
        buildConfig = true
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
                "./proguard-rules.pro",
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

secrets {
    propertiesFileName = "secrets.properties"
    defaultPropertiesFileName = "secrets.properties"
}

dependencies {
    implementation(platform(libs.supabase.bom))

    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.splashscreen)
    implementation(libs.metro.android)
    implementation(libs.supabase.auth)

    implementation(projects.entry.app)
}
