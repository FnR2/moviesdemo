package mobile.app.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import mobile.app.data.RestResult

inline fun <reified T> Flow<RestResult<T>>.buildFlow(
    dispatcher: CoroutineDispatcher,
    loading: Boolean = true
): Flow<RestResult<T>> {
    return this.onStart {
        emit(RestResult.Loading(loading))
    }.catch { exception ->
        emit(RestResult.Failure(exception))
    }.onCompletion {
        emit(RestResult.Loading(false))
    }.flowOn(dispatcher)
}
