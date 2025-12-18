package io.mishkav.voyager.convention.plugins.secrets

open class SecretsPluginExtension {
    var propertiesFileName: String = defaultPropertiesFile

    var useNameSpaceAsPackage: Boolean = true

    companion object {
        const val defaultPropertiesFile = "secrets.properties"
        const val extensionName = "voyagerSecrets"
    }
}