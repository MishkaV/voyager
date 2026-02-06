package io.mishka.voyager.core.repositories.countries.api.models.local

data class CountryWithVisitedStatus(
    val country: CountryEntity,
    val isVisited: Boolean,
)
