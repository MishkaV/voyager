import io.mishkav.voyager.convention.extensions.configureCrossPlatformDependencies
import io.mishkav.voyager.convention.extensions.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

class KotlinSerializationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("org.jetbrains.kotlin.plugin.serialization")

            configureCrossPlatformDependencies {
                commonMain(findLibrary("kotlinx.serialization"))
            }
        }
    }
}
