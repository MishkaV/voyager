import io.mishkav.voyager.convention.extensions.androidProject
import io.mishkav.voyager.convention.extensions.defaultConfigInject
import io.mishkav.voyager.convention.extensions.loadPropertiesFile
import io.mishkav.voyager.convention.plugins.secrets.SecretsPluginExtension
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import com.google.devtools.ksp.gradle.KspAATask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.util.Properties


class SecretsConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply("com.codingfeline.buildkonfig")

            val extension = project.extensions.create(
                SecretsPluginExtension.extensionName,
                SecretsPluginExtension::class.java
            )

            extensions.configure<BuildKonfigExtension> {
                val defaultProperties =
                    project.rootProject.loadPropertiesFile(extension.propertiesFileName)

                generateConfigKey(
                    project = target,
                    extension = this,
                    defaultProperties = defaultProperties
                )
            }

            if (extension.useAndroidNameSpaceAsPackage) {
                project.afterEvaluate {
                    extensions.configure<BuildKonfigExtension> {
                        androidProject()?.let { androidProject ->
                            packageName = androidProject.namespace
                        }
                    }
                }
            }
        }
    }

    private fun generateConfigKey(
        project: Project,
        extension: BuildKonfigExtension,
        defaultProperties: Properties?,
    ) {
        defaultProperties?.let {
            extension.defaultConfigInject(
                project = project,
                properties = it,
            )
        }
    }
}