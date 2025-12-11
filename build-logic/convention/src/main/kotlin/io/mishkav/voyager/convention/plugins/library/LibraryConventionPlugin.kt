import io.mishkav.voyager.convention.extensions.configureAndroidVersions
import io.mishkav.voyager.convention.extensions.configureKotlin
import com.android.build.gradle.LibraryExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class LibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("voyager.lint")
            }

            extensions.configure<LibraryExtension> {
                configureAndroidVersions<LibraryExtension>()
                configureKotlin()
            }
        }
    }
}
