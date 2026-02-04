package io.mishka.voyager.core.repositories.paging

import androidx.paging.PagingConfig

object DefaultPagingConfig {
    const val DEFAULT_PAGE_SIZE = 20

    const val DEFAULT_INITIAL_LOAD_MULTIPLIER = 3

    fun create(
        pageSize: Int = DEFAULT_PAGE_SIZE,
        prefetchDistance: Int = pageSize,
        initialLoadSize: Int = pageSize * DEFAULT_INITIAL_LOAD_MULTIPLIER,
        enablePlaceholders: Boolean = true,
    ): PagingConfig = PagingConfig(
        pageSize = pageSize,
        prefetchDistance = prefetchDistance,
        initialLoadSize = initialLoadSize,
        enablePlaceholders = enablePlaceholders,
    )
}
