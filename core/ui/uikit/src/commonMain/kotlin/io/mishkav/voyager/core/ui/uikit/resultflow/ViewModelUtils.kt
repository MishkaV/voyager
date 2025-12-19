package io.mishkav.voyager.core.ui.uikit.resultflow

import co.touchlab.kermit.Logger

inline fun <T> MutableUiStateFlow<T>.loadOrError(
    message: String? = null,
    showLoading: Boolean = true,
    load: () -> T
) {
    if (showLoading) {
        value = LoadingUiState()
    }

    value = try {
        SuccessUiState(load())
    } catch (e: Exception) {
        Logger.e("Failed to loadOrError - $e")
        ErrorUiState(message = message)
    }
}

suspend inline fun <T> MutableUiSharedFlow<T>.loadOrError(
    message: String? = null,
    showLoading: Boolean = true,
    load: () -> T
) {
    if (showLoading) {
        emit(LoadingUiState())
    }

    val value = try {
        SuccessUiState(load())
    } catch (e: Exception) {
        Logger.e("Failed to loadOrError - $e")
        ErrorUiState(message = message)
    }
    emit(value)
}
