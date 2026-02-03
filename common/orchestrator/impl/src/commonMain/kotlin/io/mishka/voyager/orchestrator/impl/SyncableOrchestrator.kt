package io.mishka.voyager.orchestrator.impl

import co.touchlab.kermit.Logger
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import io.mishka.voyager.core.repositories.base.Syncable
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.joinAll
import kotlinx.coroutines.launch

/**
 * Orchestrator for warming up (preloading) data
 *
 * All implementing [Syncable] interface will be warmed up in parallel.
 */
@SingleIn(AppScope::class)
@Inject
class SyncableOrchestrator(
    private val syncables: Set<Syncable>,
) {
    private val logger = Logger.withTag("SyncableOrchestrator")

    suspend fun syncAll() {
        if (syncables.isEmpty()) {
            logger.i { "No syncable registered" }
            return
        }

        logger.i { "Starting sync for ${syncables.size} syncable" }

        coroutineScope {
            val jobs = syncables.map { entity ->
                launch {
                    val name = entity::class.simpleName ?: "Unknown"
                    try {
                        logger.d { "Sync: $name" }
                        entity.sync()
                        logger.d { "Sync completed: $name" }
                    } catch (e: Exception) {
                        logger.e(e) { "Sync failed for $name" }
                        // Continue with other repositories
                    }
                }
            }
            jobs.joinAll()
        }

        logger.i { "Sync completed for all syncables" }
    }
}
