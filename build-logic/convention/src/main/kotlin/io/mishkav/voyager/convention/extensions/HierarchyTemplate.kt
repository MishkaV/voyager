@file:OptIn(ExperimentalKotlinGradlePluginApi::class)

package io.mishkav.voyager.convention.extensions

import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinHierarchyBuilder
import org.jetbrains.kotlin.gradle.plugin.KotlinHierarchyTemplate
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSetTree

private val hierarchyTemplate = KotlinHierarchyTemplate {
    withSourceSetTree(
        KotlinSourceSetTree.main,
        KotlinSourceSetTree.test,
    )

    common {
        withCompilations { true }

        groupNonAndroid()
        groupJvmCommon()
        groupNative()
        groupNonNative()
        groupMobile()
    }
}

private fun KotlinHierarchyBuilder.groupNonAndroid() {
    group("nonAndroid") {
        withJvm()
        groupNative()
    }
}

private fun KotlinHierarchyBuilder.groupJvmCommon() {
    group("jvmCommon") {
        withAndroidTarget()
        withJvm()
    }
}

private fun KotlinHierarchyBuilder.groupNative() {
    group("native") {
        withNative()

        group("apple") {
            withApple()

            group("ios") {
                withIos()
            }

            group("macos") {
                withMacos()
            }
        }
    }
}

private fun KotlinHierarchyBuilder.groupNonNative() {
    group("nonNative") {
        withJvm()
        withAndroidTarget()
    }
}

private fun KotlinHierarchyBuilder.groupMobile() {
    group("mobile") {
        withAndroidTarget()
        groupNative()
    }
}

fun KotlinMultiplatformExtension.applyAiutaHierarchyTemplate() {
    applyHierarchyTemplate(hierarchyTemplate)
}
