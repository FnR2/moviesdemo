package mobile.app.data

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class MovieRepository(
    private val movieService: MovieService,
    private val dispatcher: CoroutineDispatcher
) : DefaultMovieRepository {
    override suspend fun getMovies(parameters: Map<String, String>): RestResult<DiscoverResponse> =
        withContext(dispatcher) {
            return@withContext request {
                movieService.discover(parameters)
            }.mapSuccess {
                it
            }
        }

    override suspend fun getMovieDetail(movieId: Int): RestResult<MovieDetail> =
        withContext(dispatcher) {
            return@withContext request {
                movieService.getMovieDetail(movieId)
            }.mapSuccess {
                it
            }
        }

}