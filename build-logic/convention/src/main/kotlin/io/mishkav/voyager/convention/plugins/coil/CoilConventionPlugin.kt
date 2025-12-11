import io.mishkav.voyager.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class CoilConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<KotlinMultiplatformExtension> {
                sourceSets.androidMain.dependencies {
                    implementation(libs.findLibrary("ktor.engine.okhttp").get())
                }
                sourceSets.jvmMain.dependencies {
                    implementation(libs.findLibrary("ktor.engine.okhttp").get())
                }
                sourceSets.commonMain.dependencies {
                    implementation(libs.findLibrary("coil.compose").get())
                    implementation(libs.findLibrary("coil.network.ktor3").get())
                }
                sourceSets.nativeMain.dependencies {
                    implementation(libs.findLibrary("ktor.engine.darwin").get())
                }
            }
        }
    }
}
