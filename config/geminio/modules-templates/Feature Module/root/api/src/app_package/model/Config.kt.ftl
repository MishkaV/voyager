package io.mishka.voyager.${__moduleName}.api.model

import kotlinx.serialization.Serializable

@Serializable
sealed class ${__formattedModuleName}Config {

    @Serializable
    data object Screen : ${__formattedModuleName}Config()

    // TODO: Add your other screen configurations here
}
