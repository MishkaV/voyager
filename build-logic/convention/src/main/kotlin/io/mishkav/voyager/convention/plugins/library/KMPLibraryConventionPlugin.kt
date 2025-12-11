import io.mishkav.voyager.convention.extensions.addLibraryMultiplatformTargets
import io.mishkav.voyager.convention.extensions.configureKotlinMultiplatform
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KMPLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("voyager.library")
                apply("kotlin-multiplatform")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinMultiplatform<LibraryExtension>()
                addLibraryMultiplatformTargets()
            }
        }
    }
}
