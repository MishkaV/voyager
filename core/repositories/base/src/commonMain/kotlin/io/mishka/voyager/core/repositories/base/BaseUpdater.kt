package io.mishka.voyager.core.repositories.base

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration
import kotlin.time.Duration.Companion.minutes

abstract class BaseUpdater {

    suspend fun <T> retryAction(
        maxAttempts: Int = DEFAULT_MAX_ATTEMPTS,
        block: suspend () -> T,
    ): T {
        repeat(maxAttempts - 1) { attempt ->
            // Check if active
            currentCoroutineContext().ensureActive()

            // Try to update
            try {
                // Execute action
                return block()
            } catch (e: CancellationException) {
                throw e
            } catch (e: Exception) {
                Logger.e("retryAction($attempt): Failed to execute block - $e")
            }

            // Wait for new attempt if retrying
            delay(UPDATE_DELAY)
        }

        throw CancellationException("retryAction(): Coroutine scope was cancelled")
    }

    suspend fun <T> retryActionWithTimeOut(
        timeout: Duration = DEFAULT_TIMEOUT,
        maxAttempts: Int = DEFAULT_MAX_ATTEMPTS,
        block: suspend () -> T,
    ): T {
        return withTimeout(
            timeout = timeout,
        ) {
            retryAction(
                maxAttempts = maxAttempts,
                block = block,
            )
        }
    }

    private companion object {
        const val UPDATE_DELAY = 1500L
        const val DEFAULT_MAX_ATTEMPTS = 5
        val DEFAULT_TIMEOUT = 1.minutes
    }
}

fun BaseUpdater.retryAction(
    scope: CoroutineScope,
    block: suspend () -> Unit,
) {
    scope.launch { block() }
}
