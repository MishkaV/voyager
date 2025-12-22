import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension


class FeatureImplConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("voyager.library.multiplatform.compose")
                apply("voyager.coil")
                apply("voyager.decompose")
                apply("dev.zacsweers.metro")
            }

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.commonMain.dependencies {
                    implementation(project(":core:ui:lifecycle"))
                    implementation(project(":core:ui:uikit"))
                }
            }
        }
    }
}