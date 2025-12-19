import io.mishkav.voyager.convention.extensions.configureKmp
import org.gradle.api.Plugin
import org.gradle.api.Project

class KMPApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("voyager.application")
                apply("kotlin-multiplatform")
            }

            configureKmp()
        }
    }
}
