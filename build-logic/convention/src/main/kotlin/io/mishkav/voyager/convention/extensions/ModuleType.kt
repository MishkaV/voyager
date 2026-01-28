package io.mishkav.voyager.convention.extensions

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import org.gradle.api.artifacts.MinimalExternalModuleDependency
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.provider.Provider
import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/**
 * Defines the type of module based on applied plugins
 */
enum class ModuleType {
    /** Kotlin Multiplatform module */
    KMP,
    /** Pure Android module */
    ANDROID,
    /** Unknown module type */
    UNKNOWN
}

/**
 * Detects the type of the current module based on applied plugins
 */
fun Project.detectModuleType(): ModuleType {
    return when {
        pluginManager.hasPlugin("org.jetbrains.kotlin.multiplatform") -> ModuleType.KMP
        pluginManager.hasPlugin("com.android.application") ||
        pluginManager.hasPlugin("com.android.library") -> ModuleType.ANDROID
        else -> ModuleType.UNKNOWN
    }
}

/**
 * DSL scope for configuring dependencies based on module type.
 *
 * Automatically adapts dependency declarations to the module type:
 * - For KMP modules: adds to appropriate sourceSets (commonMain, androidMain, jvmMain)
 * - For Android modules: adds to dependencies block
 */
class CrossPlatformDependenciesScope(
    private val project: Project,
    private val moduleType: ModuleType
) {
    /**
     * Adds a dependency to commonMain (for KMP) or dependencies (for Android)
     *
     * @param dependencyNotation the dependency to add (e.g., Provider from libs catalog)
     */
    fun commonMain(dependencyNotation: Any) {
        when (moduleType) {
            ModuleType.KMP -> {
                project.extensions.configure<KotlinMultiplatformExtension> {
                    sourceSets.commonMain.dependencies {
                        implementation(dependencyNotation)
                    }
                }
            }
            ModuleType.ANDROID -> {
                project.dependencies {
                    add("implementation", dependencyNotation)
                }
            }
            ModuleType.UNKNOWN -> {
                project.logger.warn("Cannot add commonMain dependency: unknown module type")
            }
        }
    }

    /**
     * Adds a dependency to androidMain (for KMP) or dependencies (for Android)
     *
     * @param dependencyNotation the dependency to add (e.g., Provider from libs catalog)
     */
    fun androidMain(dependencyNotation: Any) {
        when (moduleType) {
            ModuleType.KMP -> {
                project.extensions.configure<KotlinMultiplatformExtension> {
                    sourceSets.androidMain.dependencies {
                        implementation(dependencyNotation)
                    }
                }
            }
            ModuleType.ANDROID -> {
                project.dependencies {
                    add("implementation", dependencyNotation)
                }
            }
            ModuleType.UNKNOWN -> {
                project.logger.warn("Cannot add androidMain dependency: unknown module type")
            }
        }
    }

    /**
     * Adds a dependency to jvmMain (only for KMP modules)
     *
     * For pure Android modules, this dependency is skipped (logged at debug level).
     *
     * @param dependencyNotation the dependency to add (e.g., Provider from libs catalog)
     */
    fun jvmMain(dependencyNotation: Any) {
        when (moduleType) {
            ModuleType.KMP -> {
                project.extensions.configure<KotlinMultiplatformExtension> {
                    sourceSets.jvmMain.dependencies {
                        implementation(dependencyNotation)
                    }
                }
            }
            ModuleType.ANDROID -> {
                // For pure Android modules, jvmMain doesn't make sense
                project.logger.debug("Skipping jvmMain dependency for pure Android module")
            }
            ModuleType.UNKNOWN -> {
                project.logger.warn("Cannot add jvmMain dependency: unknown module type")
            }
        }
    }

    /**
     * Direct access to the dependencies block for advanced configuration
     *
     * Use this when you need to add dependencies with custom configuration names
     * (e.g., ksp, annotationProcessor, etc.)
     *
     * @param action the configuration action
     */
    fun dependencies(action: DependencyHandlerScope.() -> Unit) {
        project.dependencies(action)
    }

    /**
     * Finds a library from the version catalog
     *
     * @param name the library name in the version catalog (e.g., "decompose")
     * @return provider for the library dependency
     */
    fun findLibrary(name: String): Provider<MinimalExternalModuleDependency> {
        return project.libs.findLibrary(name).get()
    }
}

/**
 * Configures cross-platform dependencies using a unified DSL.
 *
 * Automatically detects the module type and adapts dependency declarations accordingly.
 *
 * Example:
 * ```kotlin
 * configureCrossPlatformDependencies {
 *     commonMain(findLibrary("decompose"))
 *     androidMain(findLibrary("androidx-core"))
 *     jvmMain(findLibrary("kotlinx-coroutines-swing"))
 * }
 * ```
 *
 * @param configure the configuration action
 */
fun Project.configureCrossPlatformDependencies(
    configure: CrossPlatformDependenciesScope.() -> Unit
) {
    val moduleType = detectModuleType()
    CrossPlatformDependenciesScope(this, moduleType).configure()
}

/**
 * Applies configuration only for KMP modules.
 *
 * If the current module is not a KMP module, the action is ignored.
 *
 * @param action the configuration action for KotlinMultiplatformExtension
 */
fun Project.ifKmp(action: KotlinMultiplatformExtension.() -> Unit) {
    if (detectModuleType() == ModuleType.KMP) {
        extensions.configure<KotlinMultiplatformExtension>(action)
    }
}

/**
 * Applies configuration only for Android modules.
 *
 * If the current module is not an Android module, the action is ignored.
 *
 * @param action the configuration action for ApplicationExtension
 */
fun Project.ifAndroid(action: ApplicationExtension.() -> Unit) {
    if (detectModuleType() == ModuleType.ANDROID) {
        pluginManager.withPlugin("com.android.application") {
            extensions.configure<ApplicationExtension>(action)
        }
    }
}
