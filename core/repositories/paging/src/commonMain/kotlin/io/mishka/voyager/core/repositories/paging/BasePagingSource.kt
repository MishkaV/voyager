package io.mishka.voyager.core.repositories.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.touchlab.kermit.Logger

/**
 * Base paging source for custom Paging 3 implementations.
 * Provides common getRefreshKey logic and logging infrastructure.
 *
 * @param Key The type of the paging key (e.g., Int for page numbers, String for cursors)
 * @param Value The type of the data items being paged
 */
abstract class BasePagingSource<Key : Any, Value : Any> : PagingSource<Key, Value>() {

    /**
     * Logger instance with tag derived from paging source class name.
     */
    protected val logger: Logger = Logger.withTag(this::class.simpleName ?: "PagingSource")

    /**
     * Provides the key for refreshing the data.
     * Default implementation finds the closest page to the anchor position
     * and returns either the previous or next key.
     *
     * Override this method if you need custom refresh key logic.
     */
    override fun getRefreshKey(state: PagingState<Key, Value>): Key? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.let { page ->
                // Try to get the previous key and increment it
                page.prevKey?.let { prevKey ->
                    getNextKey(prevKey)
                }
                    ?: // If no previous key, try to get the next key and decrement it
                    page.nextKey?.let { nextKey ->
                        getPrevKey(nextKey)
                    }
            }
        }
    }

    /**
     * Calculate the next key given the current key.
     * Must be implemented by subclasses based on their key type.
     *
     * @param currentKey The current pagination key
     * @return The next key, or null if at the end
     */
    protected abstract fun getNextKey(currentKey: Key): Key?

    /**
     * Calculate the previous key given the current key.
     * Must be implemented by subclasses based on their key type.
     *
     * @param currentKey The current pagination key
     * @return The previous key, or null if at the beginning
     */
    protected abstract fun getPrevKey(currentKey: Key): Key?
}
