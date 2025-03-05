package mobile.app.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import mobile.app.data.DefaultMovieRepository
import mobile.app.data.MovieDetail
import mobile.app.data.MoviesWithGroup
import mobile.app.data.RestResult

class GetMoviesByCategoryUseCase(
    private val movieRepository: DefaultMovieRepository,
    private val dispatcher: CoroutineDispatcher
) : UseCase {
    operator fun invoke(page: Long, key: String): Flow<RestResult<MoviesWithGroup>> {
        return flow {
            val title = sortPairList[key] ?: ""
            val response = movieRepository.getMovies(key, title, page)
            emit(response)
        }.buildFlow(dispatcher)
    }
}