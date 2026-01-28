package io.mishkav.voyager.convention.extensions

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.android.build.api.dsl.LibraryExtension
import com.codingfeline.buildkonfig.compiler.FieldSpec
import com.codingfeline.buildkonfig.gradle.BuildKonfigExtension
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import java.io.FileNotFoundException
import java.util.Properties

fun Project.loadPropertiesFile(fileName: String): Properties {
    // Load file
    val propertiesFile = file(fileName)
    if (!propertiesFile.exists()) {
        throw FileNotFoundException(
            "The file '${propertiesFile.absolutePath}' could not be found"
        )
    }

    // Load contents into properties object
    return Properties().apply {
        propertiesFile.inputStream().use(::load)
    }
}

fun BuildKonfigExtension.defaultConfigInject(
    project: Project,
    properties: Properties,
) {
    val androidProject = project.androidProject()

    defaultConfigs {
        properties.keys.map { key ->
            key as String
        }.filter { key ->
            key.isNotEmpty()
        }.forEach { key ->
            val value = properties.getProperty(key).removeSurrounding("\"")

            buildConfigField(
                type = FieldSpec.Type.STRING,
                name = key,
                value = value
            )
            androidProject.manifestInject(
                key = key,
                value = value,
            )
        }

        buildConfigField(
            type = FieldSpec.Type.STRING,
            name = "VERSION_NAME",
            value = project.versionName
        )
        buildConfigField(
            type = FieldSpec.Type.INT,
            name = "VERSION_CODE",
            value = "${project.versionCode}"
        )
    }
}


fun Project.androidApplicationProject(): ApplicationExtension? =
    extensions.findByType(ApplicationExtension::class.java)

fun Project.androidLibraryProject(): LibraryExtension? =
    extensions.findByType(LibraryExtension::class.java)

fun Project.kmpAndroidLibraryProject(): KotlinMultiplatformAndroidLibraryTarget? {
    val kmpExtension = extensions.findByType(KotlinMultiplatformExtension::class.java) ?: return null
    return kmpExtension.extensions.findByType(KotlinMultiplatformAndroidLibraryTarget::class.java)
}

fun Project.androidProject(): CommonExtension? =
    androidApplicationProject() ?: androidLibraryProject()

fun CommonExtension?.manifestInject(
    key: String,
    value: String,
) {
    this?.defaultConfig?.manifestPlaceholders?.put(
        key,
        value
    )
}