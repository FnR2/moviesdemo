package mobile.app.moviesdemo

import mobile.app.data.MovieDetail
import mobile.app.data.MoviesWithGroup

class Mapper {

    fun mapMovies(model: MoviesWithGroup): DiscoverUIModel {
        return DiscoverUIModel(title = model.title, movieList = model.data.results.map {
            MovieUIModel(id = it.id, imagePath = mapImagePath(it.posterPath))
        })
    }

    fun mapMovieDetail(movie: MovieDetail): MovieDetailUIModel {
        return MovieDetailUIModel(
            id = movie.id,
            title = movie.title,
            overView = movie.overview,
            dateRelease = movie.releaseDate,
            voteAverage = String.format("%.1f", movie.voteAverage),
            imagePath = mapImagePath(movie.posterPath)
        )
    }

    private fun mapImagePath(url: String?): String? {
        return url?.let { IMAGE_PREFIX.plus(it) }
    }
}