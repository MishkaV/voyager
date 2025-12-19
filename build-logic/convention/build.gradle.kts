import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

group = "io.mishkav.voyager.convention"

// Configure the build-logic plugins to target JDK 17
// This matches the JDK used to build the project, and is not related to what is running on device.
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

dependencies {
    compileOnly(libs.gradlePlugin.android)
    compileOnly(libs.gradlePlugin.buildKonfig)
    compileOnly(libs.gradlePlugin.buildKonfig.compiler)
    compileOnly(libs.gradlePlugin.compose)
    compileOnly(libs.gradlePlugin.detekt)
    compileOnly(libs.gradlePlugin.kotlin)
    compileOnly(libs.gradlePlugin.ksp)
    compileOnly(libs.gradlePlugin.metro)
    compileOnly(libs.gradlePlugin.room)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("voyagerLint") {
            id = "voyager.lint"
            implementationClass = "LintConventionPlugin"
        }
        register("androidRoom") {
            id = "voyager.room"
            implementationClass = "AndroidRoomConventionPlugin"
        }
        register("androidKotlinSerialization") {
            id = "voyager.kotlin.serialization"
            implementationClass = "KotlinSerializationConventionPlugin"
        }
        register("voyagerLibrary") {
            id = "voyager.library"
            implementationClass = "LibraryConventionPlugin"
        }
        register("voyagerLibraryMultiplatform") {
            id = "voyager.library.multiplatform"
            implementationClass = "KMPLibraryConventionPlugin"
        }
        register("voyagerLibraryComposeMultiplatform") {
            id = "voyager.library.multiplatform.compose"
            implementationClass = "CMPLibraryConventionPlugin"
        }
        register("voyagerCoil") {
            id = "voyager.coil"
            implementationClass = "CoilConventionPlugin"
        }
        register("voyagerApplication") {
            id = "voyager.application"
            implementationClass = "ApplicationConventionPlugin"
        }
        register("voyagerApplicationMultiplatform") {
            id = "voyager.application.multiplatform"
            implementationClass = "KMPApplicationConventionPlugin"
        }
        register("voyagerSecrets") {
            id = "voyager.secrets"
            implementationClass = "SecretsConventionPlugin"
        }
        register("voyagerDecompose") {
            id = "voyager.decompose"
            implementationClass = "DecomposeConventionPlugin"
        }
        register("voyagerFeatureImpl") {
            id = "voyager.feature.impl"
            implementationClass = "FeatureImplConventionPlugin"
        }
    }
}
