package io.mishkav.voyager.convention.extensions

val supportedKspTargets by lazy {
    listOf(
        "kspAndroid",
        "kspJvm",
        "kspIosSimulatorArm64",
        "kspIosX64",
        "kspIosArm64"
    )
}