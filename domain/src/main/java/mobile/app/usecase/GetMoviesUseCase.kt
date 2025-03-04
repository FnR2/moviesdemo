package mobile.app.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import mobile.app.data.DefaultMovieRepository
import mobile.app.data.MoviesWithGroup
import mobile.app.data.RestResult

class GetMoviesUseCase(
    private val movieRepository: DefaultMovieRepository,
    private val dispatcher: CoroutineDispatcher
) : UseCase {
    operator fun invoke(): Flow<RestResult<MoviesWithGroup>> = flow {
        val channel = Channel<RestResult<MoviesWithGroup>>(capacity = Channel.UNLIMITED)
        coroutineScope {
            sortPairList.forEachIndexed { index, pair ->
                launch {
                    val result = movieRepository.getMovies(pair.first, pair.second)
                    channel.send(result)
                }
            }
        }

        for (result in channel) {
            emit(result)
        }
    }.buildFlow(dispatcher)
}

private val sortPairList: List<Pair<String, String>> = listOf(
    "revenue.desc" to "Revenue",
    "primary_release_date.asc" to "Release Date",
    "popularity.desc" to "Popular",
    "vote_average.asc" to "Top Rated"
)