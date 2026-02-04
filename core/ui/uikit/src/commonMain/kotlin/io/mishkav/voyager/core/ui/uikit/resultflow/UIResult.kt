package io.mishkav.voyager.core.ui.uikit.resultflow

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

@Immutable
sealed interface UIResult<out T> {
    data class Success<T>(val data: T) : UIResult<T>
    data class Error(val exception: Throwable) : UIResult<Nothing>
    data object Loading : UIResult<Nothing>
}

fun <T> Flow<T>.asUIResult(): Flow<UIResult<T>> = map<T, UIResult<T>> { UIResult.Success(it) }
    .onStart { emit(UIResult.Loading) }
    .catch { emit(UIResult.Error(it)) }
