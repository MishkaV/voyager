import io.mishkav.voyager.convention.extensions.configureKmp
import io.mishkav.voyager.convention.extensions.configureKotlin
import org.gradle.api.Plugin
import org.gradle.api.Project

class KMPLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("kotlin-multiplatform")
                apply("com.android.kotlin.multiplatform.library")
                apply("voyager.lint")
            }

            configureKmp()
            configureKotlin()
        }
    }
}
