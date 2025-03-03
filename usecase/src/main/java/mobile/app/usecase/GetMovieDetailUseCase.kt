package mobile.app.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mobile.app.data.DefaultMovieRepository
import mobile.app.data.DiscoverResponse
import mobile.app.data.MovieDetail
import mobile.app.data.RestResult

class GetMovieDetailUseCase(
    private val movieRepository: DefaultMovieRepository,
    private val dispatcher: CoroutineDispatcher
) : UseCase {
    fun execute(movieId: Int): Flow<RestResult<MovieDetail>> {
        return flow {
            val response = movieRepository.getMovieDetail(movieId)
            emit(response)
        }.buildFlow(dispatcher)
    }
}