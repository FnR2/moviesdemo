package mobile.app.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mobile.app.data.DefaultMovieRepository
import mobile.app.data.MovieDetail
import mobile.app.data.RestResult

class GetMovieDetailUseCase(
    private val movieRepository: DefaultMovieRepository,
    private val dispatcher: CoroutineDispatcher
) : UseCase {
    operator fun invoke(movieId: Long): Flow<RestResult<MovieDetail>> {
        return flow {
            val response = movieRepository.getMovieDetail(movieId)
            emit(response)
        }.buildFlow(dispatcher)
    }
}