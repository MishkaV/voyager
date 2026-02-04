package io.mishka.voyager

import android.content.Intent
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.touchlab.kermit.Logger
import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.coroutines.SuspendSettings
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesIntoMap
import dev.zacsweers.metro.Inject
import dev.zacsweers.metrox.viewmodel.ViewModelKey
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.auth
import io.github.jan.supabase.auth.handleDeeplinks
import io.github.jan.supabase.auth.user.UserSession
import io.mishka.voyager.core.storage.settings.VoyagerSettingsKeys
import io.mishka.voyager.orchestrator.api.IAuthOrchestrator
import io.mishka.voyager.supabase.api.ISupabaseAuth
import io.mishkav.voyager.features.navigation.api.model.VoyagerStartupStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withTimeout
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

@OptIn(ExperimentalSettingsApi::class)
@Inject
@ViewModelKey(MainViewModel::class)
@ContributesIntoMap(AppScope::class)
class MainViewModel(
    private val supabase: SupabaseClient,
    private val supabaseAuth: ISupabaseAuth,
    private val settings: SuspendSettings,
    private val authOrchestrator: IAuthOrchestrator,
) : ViewModel() {

    private val _startupStatus =
        MutableStateFlow<VoyagerStartupStatus>(VoyagerStartupStatus.Loading)
    val startupStatus = _startupStatus.asStateFlow()

    init {
        with(viewModelScope) {
            launch { authOrchestrator.startListen() }
        }
    }

    fun init(intent: Intent) {
        viewModelScope.launch {
            // Firstly, check intent
            val userSession = if (isAuthDeeplink(intent)) {
                runCatching {
                    handleSupabaseDeeplinks(intent)
                }.onFailure {
                    // Let's relogin user
                    _startupStatus.value = VoyagerStartupStatus.ShouldShowIntro
                    return@launch
                }
            } else {
                null
            }

            val isLoggedIn = supabaseAuth.isLoggedIn() || userSession != null
            val isOnboardingViewed = settings.getBoolean(
                key = VoyagerSettingsKeys.IS_ONBOARDING_VIEWED,
                defaultValue = false
            )

            _startupStatus.value = when {
                !isLoggedIn -> VoyagerStartupStatus.ShouldShowIntro
                !isOnboardingViewed -> VoyagerStartupStatus.ShouldShowOnboarding
                else -> VoyagerStartupStatus.Main
            }.also {
                Logger.d("MainViewModel: Startup status - $it")
            }
        }
    }

    private fun isAuthDeeplink(intent: Intent): Boolean {
        val data = intent.data ?: return false
        val scheme = data.scheme ?: return false
        val host = data.host ?: return false

        val supabaseAuth = supabase.auth

        return !(scheme != supabaseAuth.config.scheme || host != supabaseAuth.config.host).also {
            Logger.d("MainViewModel: isAuthDeeplink - $it")
        }
    }

    private suspend fun handleSupabaseDeeplinks(
        intent: Intent,
        timeoutMs: Long = 5_000L,
    ): UserSession {
        return withTimeout(timeoutMs) {
            suspendCancellableCoroutine { cont ->
                supabase.handleDeeplinks(
                    intent = intent,
                    onSessionSuccess = { session ->
                        Logger.d("MainViewModel: Successfully imported session from intent")
                        if (cont.isActive) cont.resume(session)
                    },
                    onError = { error ->
                        Logger.e("MainViewModel: Failed to import session from intent - ${error.message}")
                        if (cont.isActive) cont.resumeWithException(error)
                    },
                )
            }
        }
    }
}
