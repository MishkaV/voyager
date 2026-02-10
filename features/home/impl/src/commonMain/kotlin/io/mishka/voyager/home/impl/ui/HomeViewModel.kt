package io.mishka.voyager.home.impl.ui

import dev.zacsweers.metro.Inject
import io.mishka.voyager.common.audiocontroller.api.IAudioController
import io.mishka.voyager.common.audiocontroller.api.models.PlaybackState
import io.mishka.voyager.core.repositories.countries.api.ICountryGeocodingRepository
import io.mishka.voyager.core.repositories.countries.api.IUserCountriesRepository
import io.mishka.voyager.core.repositories.countrydetails.api.ICountryPodcastsRepository
import io.mishka.voyager.core.repositories.countrydetails.api.models.local.CountryPodcastEntity
import io.mishka.voyager.core.repositories.vibes.api.IVibesRepository
import io.mishka.voyager.home.impl.ui.models.CountryUIModel
import io.mishkav.voyager.core.ui.lifecycle.DecomposeViewModel
import io.mishkav.voyager.core.ui.uikit.resultflow.MutableUIResultFlow
import io.mishkav.voyager.core.ui.uikit.resultflow.UIResultFlow
import io.mishkav.voyager.core.ui.uikit.resultflow.loadOrError
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Inject
class HomeViewModel(
    private val audioController: IAudioController,
    private val countryGeocodingRepository: ICountryGeocodingRepository,
    private val vibesRepository: IVibesRepository,
    private val userCountriesRepository: IUserCountriesRepository,
    private val countryPodcastsRepository: ICountryPodcastsRepository,
) : DecomposeViewModel() {

    val selectedCountry: StateFlow<CountryUIModel?> = countryGeocodingRepository
        .activeCountry
        .flatMapLatest { country ->
            if (country == null) {
                flowOf(null)
            } else {
                // Preload podcast info
                loadPodcastInfo(country.country.id)

                vibesRepository.getVibesByCountryId(country.country.id)
                    .map { vibes ->
                        CountryUIModel(
                            country = country.country,
                            isVisited = country.isVisited,
                            vibes = vibes
                        )
                    }
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    val playbackState = combine(
        audioController.playbackState,
        audioController.playbackInfo,
        selectedCountry,
    ) { state, info, country ->
        when {
            info == null -> state
            info.countryId != country?.country?.id -> PlaybackState.IDLE
            else -> state
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = PlaybackState.IDLE
    )

    val playbackInfo = combine(
        audioController.playbackInfo,
        selectedCountry,
    ) { info, country ->
        info?.takeIf { it.countryId == country?.country?.id }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )

    private val _podcastInfoState = MutableUIResultFlow<CountryPodcastEntity?>()
    val podcastInfoState: UIResultFlow<CountryPodcastEntity?> = _podcastInfoState.asStateFlow()

    fun playPodcast(podcast: CountryPodcastEntity) {
        viewModelScope.launch {
            val info = audioController.playbackInfo

            if (info.value?.podcastId == podcast.id) {
                audioController.play()
            } else {
                audioController.loadAndPlay(
                    countryId = podcast.countryId,
                    podcastId = podcast.id,
                    audioFullPath = podcast.audioFullPatch,
                    title = podcast.title,
                    subtitle = podcast.subtitle,
                    durationSec = podcast.durationSec,
                )
            }
        }
    }

    fun seekTo(positionSec: Int) {
        viewModelScope.launch {
            audioController.seekTo(positionSec)
        }
    }

    fun seekForward() {
        viewModelScope.launch {
            audioController.seekForward()
        }
    }

    fun seekBackward() {
        viewModelScope.launch {
            audioController.seekBackward()
        }
    }

    fun pausePodcast() {
        viewModelScope.launch {
            audioController.pause()
        }
    }

    fun addOrRemoveVisitedCounty(
        countryId: String,
        isVisited: Boolean,
    ) {
        viewModelScope.launch {
            if (isVisited) {
                userCountriesRepository.addCountryToVisited(countryId)
            } else {
                userCountriesRepository.removeCountryFromVisited(countryId)
            }
        }
    }

    fun selectCountryByCoordinates(latitude: Double, longitude: Double) {
        viewModelScope.launch {
            countryGeocodingRepository.searchCountryByCoordinates(
                latitude = latitude,
                longitude = longitude
            )
        }
    }

    private fun loadPodcastInfo(countryId: String) {
        viewModelScope.launch {
            _podcastInfoState.loadOrError {
                countryPodcastsRepository
                    .getByCountryId(countryId)
                    .getOrThrow()
            }
        }
    }
}
