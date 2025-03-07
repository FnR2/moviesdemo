package mobile.app.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieRepository(
    private val movieService: MovieService,
    private val dispatcher: CoroutineDispatcher
) : DefaultMovieRepository {
    override suspend fun getMovies(
        sortBy: String,
        title: String,
        page: Long
    ): RestResult<MoviesWithGroup> =
        withContext(dispatcher) {
            return@withContext request {
                movieService.discover(sortBy, page)
            }.map {
                MoviesWithGroup(data = it, title = title, key = sortBy)
            }
        }

    override suspend fun getMovieDetail(movieId: Long): RestResult<MovieDetail> =
        withContext(dispatcher) {
            return@withContext request {
                movieService.getMovieDetail(movieId)
            }.mapSuccess {
                it
            }
        }

}