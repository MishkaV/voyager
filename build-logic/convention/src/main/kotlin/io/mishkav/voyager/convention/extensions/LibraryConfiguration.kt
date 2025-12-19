package io.mishkav.voyager.convention.extensions

import com.android.build.gradle.BaseExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.get
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun <T : BaseExtension> Project.configureAndroidVersions() {
    extensions.configure<T>("android") {
        compileSdkVersion(project.compileSdk)

        defaultConfig {
            minSdk = project.minSdk
            targetSdk = project.targetSdk
            versionCode = project.versionCode
            versionName = project.versionName

            vectorDrawables {
                useSupportLibrary = true
            }
        }


        compileOptions {
            sourceCompatibility = projectJavaVersion
            targetCompatibility = projectJavaVersion
        }
    }
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
fun Project.configureKmp() {
    version = project.versionName

    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinMultiplatformExtension> {
            applyVoyagerHierarchyTemplate()

            jvmToolchain(projectJavaVersion.majorVersion.toInt())

            androidTarget()

            // Should migrate with stable AGP 9.0
            // See https://youtrack.jetbrains.com/projects/KT/issues/KT-77971/Unresolved-Reference-for-KMP-Api-Dependency-2-Modules-Down
//            androidLibrary {
//                compileSdk = project.compileSdk
//                minSdk = project.minSdk
//
//                compilerOptions {
//                    jvmTarget.set(projectJvmTarget)
//                }
//            }

            jvm()
        }
    }
}

fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            // Treat all Kotlin warnings as errors (disabled by default)
            // Override by setting warningsAsErrors=true in your ~/.gradle/gradle.properties
            val warningsAsErrors: String? by project
            allWarningsAsErrors.set(warningsAsErrors.toBoolean())
            freeCompilerArgs.add(
                // Enable experimental coroutines APIs, including Flow
                "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
            )
        }
    }
}
