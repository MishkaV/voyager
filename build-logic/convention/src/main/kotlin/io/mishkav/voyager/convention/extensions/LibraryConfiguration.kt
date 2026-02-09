package io.mishkav.voyager.convention.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun <T : ApplicationExtension> Project.configureAndroidVersions() {
    extensions.configure<T>("android") {
        compileSdk {
            version = release(project.compileSdk)
        }

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
    plugins.withId("org.jetbrains.kotlin.multiplatform") {
        extensions.configure<KotlinMultiplatformExtension> {
            applyVoyagerHierarchyTemplate()

            jvmToolchain(projectJavaVersion.majorVersion.toInt())

            jvm()

            extensions.configure<KotlinMultiplatformAndroidLibraryTarget> {
                compileSdk = project.compileSdk
                minSdk = project.minSdk

                if (namespace == null) {
                    // Default namespace
                    namespace =
                        "${project.group.toString().lowercase().replace("-", ".")}.${project.name.replace("-", ".")}"
                }

                compilerOptions {
                    jvmTarget.set(projectJvmTarget)
                }
            }
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
            freeCompilerArgs.addAll(
                listOf(
                    // Enable experimental coroutines APIs, including Flow
                    "-opt-in=kotlinx.coroutines.ExperimentalCoroutinesApi",
                    // Enable experimental material3 expressive APIs - remove with stable version
                    "-opt-in=androidx.compose.material3.ExperimentalMaterial3ExpressiveApi",
                    // Dismiss expect/actual
                    "-Xexpect-actual-classes"
                )
            )
        }
    }
}
