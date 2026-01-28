import io.mishkav.voyager.convention.extensions.configureCrossPlatformDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class DecomposeConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("voyager.kotlin.serialization")

            configureCrossPlatformDependencies {
                commonMain(findLibrary("decompose"))
                commonMain(findLibrary("decompose.extensions.compose"))
                commonMain(findLibrary("decompose.extensions.compose.experimental"))
                commonMain(findLibrary("essenty.lifecycle.coroutines"))
            }
        }
    }
}