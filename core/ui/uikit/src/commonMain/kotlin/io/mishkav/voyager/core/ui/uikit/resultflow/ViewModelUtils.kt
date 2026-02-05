package io.mishkav.voyager.core.ui.uikit.resultflow

import co.touchlab.kermit.Logger
import kotlinx.coroutines.CancellationException

inline fun <T> MutableUIResultFlow<T>.loadOrError(
    showLoading: Boolean = true,
    load: () -> T
) {
    if (showLoading) {
        value = UIResult.Loading()
    }

    value = try {
        UIResult.Success(load())
    } catch (e: CancellationException) {
        throw e
    } catch (e: Exception) {
        Logger.e("Failed to loadOrError - $e", e)
        UIResult.Error(e)
    }
}
