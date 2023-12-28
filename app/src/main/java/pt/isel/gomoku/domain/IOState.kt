package pt.isel.gomoku.domain

import pt.isel.gomoku.http.service.result.APIResult
import pt.isel.gomoku.http.service.result.APIException

/**
 * Sum type that represents the state of a I/O operation.
 */
sealed class IOState<out T>

/**
 * The idle state, i.e. the state before the load operation is started.
 */
data object Idle : IOState<Nothing>()

/**
 * The loading state, i.e. the state while the load operation is in progress.
 */
data object Loading : IOState<Nothing>()

/**
 * The loaded state, i.e. the state after the load operation has finished.
 * @param value the result of the load operation.
 */
data class Loaded<T>(val value: APIResult<T>) : IOState<T>()

/**
 * Returns a new [IOState] in the idle state.
 */
fun idle(): Idle = Idle

/**
 * Returns a new [IOState] in the loading state.
 */
fun loading(): Loading = Loading

/**
 * Returns a new [IOState] in the loaded state.
 */
fun <T> loaded(value: APIResult<T>): Loaded<T> = Loaded(value)

/**
 * Returns a new [IOState] in the loaded state with a successful result.
 */
fun <T> loadSuccess(value: T): Loaded<T> = loaded(APIResult.success(value))

/**
 * Returns a new [IOState] in the loaded state with a failed result.
 */
fun <T> loadFailure(error: APIException): Loaded<Nothing> = loaded(APIResult.failure(error))

/**
 * Returns the result of the I/O operation, if one is available.
 */
fun <T> IOState<T>.getOrNull(): T? = when (this) {
    is Loaded -> value.getOrNull()
    else -> null
}

/**
 * Returns the result of the I/O operation, if one is available, or throws
 * the exception that caused the operation to fail. If the operation
 * is still in progress, an [IllegalStateException] is thrown.
 */
fun <T> IOState<T>.getOrThrow(): T = when (this) {
    is Loaded -> value.getOrThrow()
    else -> throw IllegalStateException("No value available")
}

/**
 * Returns the exception that caused the I/O operation to fail, if one is available.
 */
fun <T> IOState<T>.exceptionOrNull(): Throwable? = when (this) {
    is Loaded -> value.exceptionOrNull()
    else -> null
}