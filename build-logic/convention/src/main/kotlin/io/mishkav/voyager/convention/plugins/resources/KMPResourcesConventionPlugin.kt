import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import io.mishkav.voyager.convention.extensions.configureKmp
import io.mishkav.voyager.convention.extensions.configureKotlin
import io.mishkav.voyager.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KMPResourcesConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            extensions.configure<KotlinMultiplatformExtension> {
                extensions.configure<KotlinMultiplatformAndroidLibraryTarget> {
                    androidResources {
                        enable = true
                    }
                }
                sourceSets.commonMain.dependencies {
                    implementation(libs.findLibrary("jetbrains.compose.components.resources").get())
                }
            }
        }
    }
}
