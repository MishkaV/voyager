plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.android.kmp.library) apply false
    alias(libs.plugins.buildKonfig) apply false
    alias(libs.plugins.jetbrains.compose) apply false
    alias(libs.plugins.jetbrains.compose.compiler) apply false
    alias(libs.plugins.jetbrains.compose.hotreload) apply false
    alias(libs.plugins.kotlinx.serialization) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.metro) apply false
    alias(libs.plugins.room) apply false
    alias(libs.plugins.detekt)
}

tasks.register<Copy>("installGitHooks") {
    val gitHooksFileNames = listOf(
        "pre-push",
    )

    group = "build setup"
    description = "Installs Git hooks from scripts/githooks to .git/hooks/"
    val hooksFilesToCopy = rootProject.files(
        gitHooksFileNames.map { fileName ->
            file("scripts/githooks/$fileName")
        }
    )
    from(hooksFilesToCopy)
    val gitHooksDir = rootProject.file(".git/hooks")
    into(gitHooksDir)

    doLast {
        rootProject
            .files(gitHooksFileNames.map { fileName -> File(gitHooksDir, fileName) })
            .forEach { hookFile -> hookFile.setExecutable(true) }
        println("✅ Git hook installed successfully!")
    }
}