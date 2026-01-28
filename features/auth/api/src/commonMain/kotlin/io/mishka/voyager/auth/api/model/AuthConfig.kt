package io.mishka.voyager.auth.api.model

import kotlinx.serialization.Serializable

@Serializable
sealed class AuthConfig {
    @Serializable
    data object Login : AuthConfig()

    @Serializable
    data object AskEmail : AuthConfig()

    @Serializable
    data object InsertOTP : AuthConfig()
}
