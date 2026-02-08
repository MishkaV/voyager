package io.mishkav.voyager.features.navigation.api.bottomsheet

import kotlinx.serialization.Serializable

@Serializable
sealed interface SheetConfig {

    @Serializable
    data class RequestVoyagerAI(
        val aiSuggestId: String,
        val backgroundHex: String,
        val countryId: String,
    ) : SheetConfig
}
