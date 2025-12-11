import io.mishkav.voyager.convention.extensions.addAppMultiplatformTargets
import io.mishkav.voyager.convention.extensions.configureKotlinMultiplatform
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

class KMPApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("voyager.application")
                apply("kotlin-multiplatform")
            }

            extensions.configure<BaseAppModuleExtension> {
                configureKotlinMultiplatform<BaseAppModuleExtension>()
                addAppMultiplatformTargets()
            }
        }
    }
}
