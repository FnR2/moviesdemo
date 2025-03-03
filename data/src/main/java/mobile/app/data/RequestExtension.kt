package mobile.app.data

import retrofit2.HttpException

suspend fun <T> request(call: suspend () -> T): RestResult<T> {
    return try {
        val response = call()
        RestResult.Success(response)
    } catch (e: HttpException) {
        RestResult.Failure(e)
    }
}
