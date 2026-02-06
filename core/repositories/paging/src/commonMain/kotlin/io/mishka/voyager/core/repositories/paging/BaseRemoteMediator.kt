package io.mishka.voyager.core.repositories.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.RoomDatabase
import androidx.room.immediateTransaction
import androidx.room.useWriterConnection
import co.touchlab.kermit.Logger

/**
 * Base remote mediator for implementing network + database pattern.
 *
 * RemoteMediator handles loading data from network and saving it to the database.
 * The PagingSource then reads from the database, providing a single source of truth.
 *
 * @param Key The type of the paging key
 * @param Value The type of the data items being paged
 * @param database The Room database instance for transaction support
 */
@OptIn(ExperimentalPagingApi::class)
abstract class BaseRemoteMediator<Key : Any, Value : Any>(
    protected val database: RoomDatabase,
) : RemoteMediator<Key, Value>() {

    /**
     * Logger instance with tag derived from mediator class name.
     */
    protected val logger: Logger =
        Logger.withTag("${Logger.tag}: ${this::class.simpleName ?: "RemoteMediator"}")

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Key, Value>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> {
                    logger.d { "Load type: REFRESH" }
                    getRefreshKey(state)
                }

                LoadType.PREPEND -> {
                    logger.d { "Load type: PREPEND" }
                    getPrependKey(state)
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }

                LoadType.APPEND -> {
                    logger.d { "Load type: APPEND" }
                    getAppendKey(state)
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }

            // Fetch data from network
            val items = fetchRemote(loadKey, state.config.pageSize)

            // Save to database
            database.useWriterConnection<Unit> { transactor ->
                try {
                    transactor.immediateTransaction {
                        if (loadType == LoadType.REFRESH) {
                            clearDatabase()
                        }
                        saveToDatabase(items)
                    }
                } catch (e: Exception) {
                    throw e
                }
            }

            MediatorResult.Success(endOfPaginationReached = items.isEmpty())
        } catch (e: Exception) {
            logger.e(e) { "Error loading data from remote" }
            MediatorResult.Error(e)
        }
    }

    /**
     * Get the key for refreshing data (typically null to start from the beginning).
     * Override if you need custom refresh logic.
     */
    protected open fun getRefreshKey(state: PagingState<Key, Value>): Key? = null

    /**
     * Get the key for prepending data (loading previous page).
     * Return null if prepending is not supported.
     */
    protected abstract fun getPrependKey(state: PagingState<Key, Value>): Key?

    /**
     * Get the key for appending data (loading next page).
     * Return null if we've reached the end.
     */
    protected abstract fun getAppendKey(state: PagingState<Key, Value>): Key?

    /**
     * Fetch data from remote source.
     *
     * @param loadKey The key for the page to load (null for first page)
     * @param loadSize The number of items to load
     * @return List of items fetched from remote
     */
    protected abstract suspend fun fetchRemote(loadKey: Key?, loadSize: Int): List<Value>

    /**
     * Save items to the database.
     * Called within a database transaction.
     */
    protected abstract suspend fun saveToDatabase(items: List<Value>)

    /**
     * Clear all data from the database.
     * Called during refresh before saving new data.
     */
    protected abstract suspend fun clearDatabase()
}
