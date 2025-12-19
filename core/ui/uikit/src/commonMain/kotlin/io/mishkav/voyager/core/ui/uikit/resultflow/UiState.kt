package io.mishkav.voyager.core.ui.uikit.resultflow

import androidx.compose.runtime.Immutable

@Immutable
sealed interface UiState<out T>

class ErrorUiState<T>(val message: String? = null) : UiState<T>

class LoadingUiState<T> : UiState<T>

class NothingUiState<T> : UiState<T>

class SuccessUiState<T>(val data: T) : UiState<T>

fun <T> UiState<T>.isErrorState(): Boolean {
    return this is ErrorUiState<T>
}

fun <T> UiState<T>.isLoadingState(): Boolean {
    return this is LoadingUiState<T>
}

fun <T> UiState<T>.isNothingState(): Boolean {
    return this is NothingUiState<T>
}

fun <T> UiState<T>.isSuccessState(): Boolean {
    return this is SuccessUiState<T>
}

fun <T> UiState<T>.getSuccessResult(): T? {
    return (this as? SuccessUiState<T>)?.data
}
