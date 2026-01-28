import org.gradle.api.Plugin
import org.gradle.api.Project

class KMPApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("voyager.library.multiplatform")
            }
        }
    }
}
