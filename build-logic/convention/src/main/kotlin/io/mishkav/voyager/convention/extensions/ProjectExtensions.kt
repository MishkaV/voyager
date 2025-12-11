package io.mishkav.voyager.convention.extensions

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import kotlin.math.pow

val projectJavaVersion: JavaVersion = JavaVersion.VERSION_17
val projectJvmTarget: JvmTarget = JvmTarget.JVM_17

val Project.minSdk: Int
    get() = intProperty("MIN_SDK")

val Project.targetSdk: Int
    get() = intProperty("TARGET_SDK")

val Project.compileSdk: Int
    get() = intProperty("COMPILE_SDK")

val Project.groupId: String
    get() = stringProperty("GROUP_ID")

val Project.versionName: String
    get() = stringProperty("VERSION")

val Project.libs
    get(): VersionCatalog = extensions.getByType<VersionCatalogsExtension>().named("libs")

val Project.versionCode: Int
    get() =
        versionName
            .takeWhile { it.isDigit() || it == '.' }
            .split('.')
            .map { it.toInt() }
            .reversed()
            .sumByIndexed { index, unit ->
                // 3.2.1 -> 302010
                (unit * 10.0.pow(2 * index + 1)).toInt()
            }

private fun Project.intProperty(
    name: String,
    default: () -> Int = { error("unknown property: $name") },
): Int = (properties[name] as String?)?.toInt() ?: default()

private fun Project.stringProperty(
    name: String,
    default: () -> String = { error("unknown property: $name") },
): String = (properties[name] as String?) ?: default()

private fun Project.booleanProperty(
    name: String,
    default: () -> Boolean = { error("unknown property: $name") },
): Boolean = (properties[name] as String?)?.toBooleanStrict() ?: default()

private inline fun <T> List<T>.sumByIndexed(selector: (Int, T) -> Int): Int {
    var index = 0
    var sum = 0
    for (element in this) {
        sum += selector(index++, element)
    }
    return sum
}
