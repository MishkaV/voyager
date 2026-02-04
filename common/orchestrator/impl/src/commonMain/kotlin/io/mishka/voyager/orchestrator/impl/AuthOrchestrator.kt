package io.mishka.voyager.orchestrator.impl

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.auth.status.SessionStatus
import io.mishka.voyager.orchestrator.api.IAuthOrchestrator
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@ContributesBinding(AppScope::class)
class AuthOrchestrator(
    private val supabaseAuth: Auth,
    private val syncableOrchestrator: SyncableOrchestrator,
//     private val cleanupOrchestrator: CleanupOrchestrator,
) : IAuthOrchestrator {

    private val logger by lazy { Logger.withTag("${Logger.tag}: AuthOrchestrator") }

    override suspend fun startListen() {
        logger.d { "startListen()" }

        supabaseAuth.sessionStatus
            .onEach { status ->
                logger.d { "startListen(): new sessionStatus - $status" }

                when (status) {
                    is SessionStatus.Authenticated -> syncableOrchestrator.syncAll()
                    is SessionStatus.NotAuthenticated -> {
//                        if (status.isSignOut) {
//                            cleanupOrchestrator.cleanupAll()
//                        }
                    }
                    is SessionStatus.Initializing -> Unit
                    is SessionStatus.RefreshFailure -> Unit
                }
            }
            .collect()
    }
}
