package mobile.app.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mobile.app.data.DefaultMovieRepository
import mobile.app.data.DiscoverResponse
import mobile.app.data.RestResult
import okhttp3.Dispatcher

class GetMoviesUseCase(
    private val movieRepository: DefaultMovieRepository,
    private val dispatcher: CoroutineDispatcher
) : UseCase {
    fun execute(): Flow<RestResult<DiscoverResponse>> {
        return flow {
            val response = movieRepository.getMovies(emptyMap())
            emit(response)
        }.buildFlow(dispatcher)
    }
}