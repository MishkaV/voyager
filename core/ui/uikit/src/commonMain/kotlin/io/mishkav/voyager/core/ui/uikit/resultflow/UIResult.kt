package io.mishkav.voyager.core.ui.uikit.resultflow

import androidx.compose.runtime.Immutable
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.contract

@Immutable
sealed interface UIResult<out T> {
    data class Success<T>(val data: T) : UIResult<T>

    data class Error<T>(val exception: Throwable) : UIResult<T>

    class Loading<T> : UIResult<T>

    class Nothing<T> : UIResult<T>
}

fun <T> Flow<T>.asUIResult(): Flow<UIResult<T>> = map<T, UIResult<T>> { UIResult.Success(it) }
    .onStart { emit(UIResult.Loading()) }
    .catch { emit(UIResult.Error(it)) }

fun <T> UIResult<T>.successOrNull(): T? = (this as? UIResult.Success)?.data

@OptIn(ExperimentalContracts::class)
fun <T> UIResult<T>.isLoading(): Boolean {
    contract {
        returns(true) implies (this@isLoading is UIResult.Loading)
    }

    return this is UIResult.Loading
}

@OptIn(ExperimentalContracts::class)
fun <T> UIResult<T>.isSuccess(): Boolean {
    contract {
        returns(true) implies (this@isSuccess is UIResult.Success)
    }

    return this is UIResult.Success
}

@OptIn(ExperimentalContracts::class)
fun <T> UIResult<T>.isError(): Boolean {
    contract {
        returns(true) implies (this@isError is UIResult.Error)
    }

    return this is UIResult.Error
}
