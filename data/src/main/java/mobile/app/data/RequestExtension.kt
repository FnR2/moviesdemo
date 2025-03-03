package mobile.app.data

import retrofit2.HttpException

suspend fun <T> request(call: suspend () -> RestResponse<T>): RestResult<T> {
    return try {
        val response = call()
        if (response.result != null)
            RestResult.Success(response.result)
        else {
            RestResult.Failure(response.error)
        }
    } catch (e: HttpException) {
        RestResult.Failure(e)
    }
}
