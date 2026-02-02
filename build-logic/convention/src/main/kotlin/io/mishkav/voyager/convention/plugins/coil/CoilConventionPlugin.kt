import io.mishkav.voyager.convention.extensions.configureCrossPlatformDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class CoilConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("voyager.ktor")

            configureCrossPlatformDependencies {
                commonMain(findLibrary("coil.compose"))
                commonMain(findLibrary("coil.network.ktor3"))
            }
        }
    }
}
