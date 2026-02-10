package io.mishka.voyager.core.repositories.countries.api.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NominatimResponseDTO(
    @SerialName("place_id")
    val placeId: Long? = null,

    @SerialName("lat")
    val lat: String? = null,

    @SerialName("lon")
    val lon: String? = null,

    @SerialName("type")
    val type: String? = null,

    @SerialName("addresstype")
    val addressType: String? = null,

    @SerialName("name")
    val name: String? = null,

    @SerialName("display_name")
    val displayName: String? = null,

    @SerialName("address")
    val address: NominatimAddressDTO? = null,

    @SerialName("boundingbox")
    val boundingBox: List<String>? = null,
)

@Serializable
data class NominatimAddressDTO(
    @SerialName("country")
    val country: String? = null,

    @SerialName("country_code")
    val countryCode: String? = null,
)
