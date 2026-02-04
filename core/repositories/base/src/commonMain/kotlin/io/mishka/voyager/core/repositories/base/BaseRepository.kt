package io.mishka.voyager.core.repositories.base

import co.touchlab.kermit.Logger

abstract class BaseRepository : BaseUpdater() {

    protected val logger: Logger = Logger.withTag("${Logger.tag}: ${this::class.simpleName ?: "Repository"}")

    /**
     * Will try to get from local, if failed will try to get from backend
     */
    suspend fun <Entity> restartableLoad(
        forceUpdate: Boolean = false,
        remoteLoad: suspend () -> Entity,
        localLoad: suspend () -> Entity,
        replaceLocalData: suspend (Entity) -> Unit,
    ): Result<Entity> = retryAction {
        val remoteGetAndUpdate: suspend () -> Entity = {
            // Get from backend
            val remoteItem = remoteLoad()

            // Update and return
            replaceLocalData(remoteItem)
            remoteItem
        }

        if (forceUpdate) {
            remoteGetAndUpdate()
        } else {
            try {
                localLoad()
            } catch (e: Exception) {
                // Can get from local, let's move to remote
                logger.e { "restartableLoad(): Failed to load from local - $e" }
                remoteGetAndUpdate()
            }
        }
    }

    // Added force to remote/local load, because same signature with another restartableLoad
    suspend fun <DTO, Entity> restartableLoad(
        forceUpdate: Boolean = false,
        remoteLoad: suspend (forceUpdate: Boolean) -> DTO,
        localLoad: suspend (forceUpdate: Boolean) -> Entity,
        replaceLocalData: suspend (DTO) -> Entity,
    ): Result<Entity> = retryAction {
        val remoteGetAndUpdate: suspend () -> Entity = {
            // Get from backend
            val remoteItem = remoteLoad(forceUpdate)

            // Update and return
            replaceLocalData(remoteItem)
        }

        if (forceUpdate) {
            remoteGetAndUpdate()
        } else {
            try {
                localLoad(forceUpdate)
            } catch (e: Exception) {
                // Can get from local, let's move to remote
                logger.e { "restartableLoad(): Failed to load from local - $e" }
                remoteGetAndUpdate()
            }
        }
    }
}
