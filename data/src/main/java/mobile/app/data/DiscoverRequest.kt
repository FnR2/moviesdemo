package mobile.app.data


data class DiscoverRequest(
    val includeAdult: Boolean,
    val includeVideo: Boolean,
    val language: String,
    val page: Int,
    val sortBy: String
)
