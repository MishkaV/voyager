package io.mishka.voyager.core.repositories.countries.api.models.local

import androidx.room.Embedded

data class CountryWithVisitedStatusRoom(
    @Embedded val country: CountryEntity,
    val isVisited: Int,
) {
    fun toDomainModel(): CountryWithVisitedStatus = CountryWithVisitedStatus(
        country = country,
        isVisited = isVisited == 1,
    )
}
