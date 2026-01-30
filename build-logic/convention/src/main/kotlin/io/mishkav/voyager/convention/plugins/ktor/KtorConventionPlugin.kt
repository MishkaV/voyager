import io.mishkav.voyager.convention.extensions.configureCrossPlatformDependencies
import org.gradle.api.Plugin
import org.gradle.api.Project

class KtorConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {

            configureCrossPlatformDependencies {
                androidMain(findLibrary("ktor.engine.okhttp"))
                jvmMain(findLibrary("ktor.engine.okhttp"))
            }
        }
    }
}
