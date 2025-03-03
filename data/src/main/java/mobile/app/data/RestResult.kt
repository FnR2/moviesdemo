package mobile.app.data

sealed class RestResult<out T> {
    data class Success<T>(val data: T) : RestResult<T>()
    data class Failure(val exception: Throwable) : RestResult<Nothing>()
    data class Loading(val isShowing: Boolean) : RestResult<Nothing>()

    fun <R> map(transform: (T) -> R): RestResult<R> {
        return when (this) {
            is Success -> Success(transform(data))
            is Failure -> this
            is Loading -> this
        }
    }

    fun mapSuccess(action: (T) -> Unit): RestResult<T> {
        if (this is Success) action(data)
        return this
    }
}
