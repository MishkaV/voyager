import io.mishkav.voyager.convention.extensions.libs
import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.Lint
import com.android.build.gradle.LibraryExtension
import io.gitlab.arturbosch.detekt.Detekt
import io.gitlab.arturbosch.detekt.DetektPlugin
import io.gitlab.arturbosch.detekt.extensions.DetektExtension
import io.gitlab.arturbosch.detekt.report.ReportMergeTask
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.TaskProvider
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.getByType
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.register
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.io.File
import kotlin.text.set

private fun Lint.configure() {
    xmlReport = true
    checkDependencies = true
    warningsAsErrors = true
    disable += listOf(
        // Ignore updating via lint - we check it with renovate
        "NewerVersionAvailable",
        "AndroidGradlePluginVersion",
        "GradleDependency",

        // In our DS we not support monochrome icons
        "MonochromeLauncherIcon",
    )
}

class LintConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) {
        with(target) {
            // Android lint
            when {
                pluginManager.hasPlugin("com.android.application") ->
                    configure<ApplicationExtension> { lint(Lint::configure) }

                pluginManager.hasPlugin("com.android.library") ->
                    configure<LibraryExtension> { lint(Lint::configure) }

                else -> {
                    pluginManager.apply("com.android.lint")
                    configure<Lint>(Lint::configure)
                }
            }

            // Detekt
            configureDetekt()
        }
    }
}

@Suppress("Unchecked_cast")
fun Project.configureDetekt() {
    pluginManager.apply("io.gitlab.arturbosch.detekt")

    val reportMerge = if (!rootProject.tasks.names.contains("reportMerge")) {
        rootProject.tasks.register("reportMerge", ReportMergeTask::class) {
            output.set(rootProject.layout.buildDirectory.file("reports/detekt/merge.xml"))
        }
    } else {
        rootProject.tasks.named("reportMerge") as TaskProvider<ReportMergeTask>
    }

    extensions.getByType<DetektExtension>().apply {
        // parallel processing
        parallel = true
        // detekt configuration file
        config.setFrom(
            File(rootProject.rootDir, "config/detekt/detekt.yaml"),
        )
        // apply your own configuration file on top of the default settings
        buildUponDefaultConfig = true
        // do not let them fail when there is a rule violation
        ignoreFailures = false
    }

    plugins.withType<DetektPlugin> {
        tasks.withType<Detekt> detekt@{
            finalizedBy(reportMerge)

            source = project.files("./").asFileTree

            include("**/src/*/kotlin/**/*.kt")
            exclude(
                "**/resources/**",
                "**/build/**",
                // Icons
                "**/io/mishkav/voyager/core/ui/theme/icons/**",
            )

            reportMerge.configure {
                input.from(this@detekt.xmlReportFile)
            }
        }
    }

    dependencies {
        "detektPlugins"(libs.findLibrary("detekt.ruleset.compiler").get())
        "detektPlugins"(libs.findLibrary("detekt.ruleset.ktlint").get())
        "detektPlugins"(libs.findLibrary("detekt.ruleset.compose").get())
        "detektPlugins"(libs.findLibrary("detekt.ruleset.decompose").get())
    }
}