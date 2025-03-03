
package mobile.app.data

data class DiscoverResponse (
    val page: Long,
    val totalPages: Long,
    val results: List<Movie>,
    val totalResults: Long
)

data class Movie (
    val overview: String,
    val originalLanguage: String,
    val originalTitle: String,
    val video: Boolean,
    val title: String,
    val genreIDS: List<Long>,
    val posterPath: String,
    val backdropPath: String,
    val releaseDate: String,
    val popularity: Double,
    val voteAverage: Double,
    val id: Long,
    val adult: Boolean,
    val voteCount: Long
)
