package io.mishkav.voyager.core.ui.uikit.resultflow

import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

typealias UiStateFlow<T> = StateFlow<UiState<T>>
typealias MutableUiStateFlow<T> = MutableStateFlow<UiState<T>>

@Suppress("FunctionName")
fun <T> MutableUiStateFlow(
    value: UiState<T> = NothingUiState()
): MutableUiStateFlow<T> = MutableStateFlow(value)

typealias UiSharedFlow<T> = SharedFlow<UiState<T>>
typealias MutableUiSharedFlow<T> = MutableSharedFlow<UiState<T>>

@Suppress("FunctionName")
fun <T> MutableUiSharedFlow(
    replay: Int = 0,
    extraBufferCapacity: Int = 0,
    onBufferOverflow: BufferOverflow = BufferOverflow.SUSPEND
): MutableUiSharedFlow<T> = MutableSharedFlow(replay, extraBufferCapacity, onBufferOverflow)
