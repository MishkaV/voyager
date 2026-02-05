package io.mishkav.voyager.core.ui.uikit.resultflow

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

typealias UIResultFlow<T> = StateFlow<UIResult<T>>
typealias MutableUIResultFlow<T> = MutableStateFlow<UIResult<T>>

fun <T> MutableUIResultFlow(
    value: UIResult<T> = UIResult.Loading()
): MutableUIResultFlow<T> = MutableStateFlow(value)
