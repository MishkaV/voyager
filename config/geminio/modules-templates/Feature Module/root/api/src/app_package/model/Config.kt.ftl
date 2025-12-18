package io.mishka.voyager.${__moduleName}.api.model

import kotlinx.serialization.Serializable

/**
 * Configuration for ${__formattedModuleName} feature navigation.
 */
@Serializable
sealed class ${__formattedModuleName}Config {

    @Serializable
    data object Screen : ${__formattedModuleName}Config()

    // TODO: Add your other screen configurations here
}
