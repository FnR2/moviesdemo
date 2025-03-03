package mobile.app.data

data class RestResponse<T>(val result: T?, val error: Throwable)
