import io.mishkav.voyager.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class DecomposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("voyager.kotlin.serialization")

            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.commonMain.dependencies {
                    implementation(libs.findLibrary("decompose").get())
                    implementation(libs.findLibrary("decompose.extensions.compose").get())
                    implementation(libs.findLibrary("decompose.extensions.compose.experimental").get())
                    implementation(libs.findLibrary("essenty.lifecycle.coroutines").get())
                }
            }
        }
    }
}