import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradlePluginExtension


class CMPLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("voyager.library.multiplatform")
                apply("org.jetbrains.kotlin.plugin.compose")
                apply("org.jetbrains.compose")
            }

            extensions.configure<ComposeCompilerGradlePluginExtension>() {

                stabilityConfigurationFiles
                    .add(isolated.rootProject.projectDirectory.file("compose_compiler_config.conf"))
            }
        }
    }
}
