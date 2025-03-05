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
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("release_date")
    val releaseDate: String?,
    @SerializedName("title")
    val title: String,
    @SerializedName("overview")
    val overview: String,
) {
    fun toMovieEntity(key: String): MovieEntity {
        return MovieEntity(
            posterPath = posterPath,
            id = id,
            voteAverage = voteAverage,
            releaseDate = releaseDate,
            title = title,
            overview = overview,
            groupKey = key
        )
    }
}

data class MoviesWithGroup(val data: DiscoverResponse, val title: String, val key: String) {
    fun toMovieWithGroupEntity(): MovieWithGroupEntity {
        return MovieWithGroupEntity(
            page = data.page,
            title = title,
            totalPages = data.totalPages,
            groupKey = key,
            totalResults = data.totalResults
        )
    }
}