package mobile.app.data

import com.google.gson.annotations.SerializedName

data class DiscoverResponse(
    @SerializedName("page")
    val page: Long,
    @SerializedName("total_pages")
    val totalPages: Long,
    @SerializedName("results")
    val results: List<Movie>,
    @SerializedName("total_results")
    val totalResults: Long
)

data class Movie(
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("id")
    val id: Long,
)

data class MoviesWithGroup(val data: DiscoverResponse, val title: String)