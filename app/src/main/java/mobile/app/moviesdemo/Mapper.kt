package mobile.app.moviesdemo

import mobile.app.data.Movie
import mobile.app.data.MovieDetail
import mobile.app.data.MoviesWithGroup
import mobile.app.data.orZero
import mobile.app.moviesdemo.viewmodel.DiscoverUIModel
import mobile.app.moviesdemo.viewmodel.MovieDetailUIModel

class Mapper {

    fun mapDiscoverMovies(model: MoviesWithGroup): DiscoverUIModel {
        return DiscoverUIModel(
            title = model.title,
            key = model.key,
            movieList = mapMoviesByCategory(model.data.results),
            currentPage = model.data.page
        )
    }

    fun mapMovieDetail(movie: MovieDetail): MovieDetailUIModel {
        return MovieDetailUIModel(
            id = movie.id,
            title = movie.title,
            overView = movie.overview,
            dateRelease = movie.releaseDate ?: "",
            voteAverage = String.format("%.1f", movie.voteAverage.orZero()),
            imagePath = mapImagePath(movie.posterPath)
        )
    }

    fun mapMoviesByCategory(movies: List<Movie>): MutableList<MovieDetailUIModel> {
        return movies.map {
            MovieDetailUIModel(
                id = it.id,
                imagePath = mapImagePath(it.posterPath),
                title = it.title.orEmpty(),
                overView = it.overview.orEmpty(),
                dateRelease = it.releaseDate.orEmpty(),
                voteAverage = String.format("%.1f", it.voteAverage.orZero()),
            )
        }.toMutableList()
    }

    private fun mapImagePath(url: String?): String? {
        return url?.let { IMAGE_PREFIX.plus(it) }
    }
}