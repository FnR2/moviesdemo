package mobile.app.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.launch
import mobile.app.data.DefaultMovieRepository
import mobile.app.data.MoviesWithGroup
import mobile.app.data.RestResult

class GetMoviesUseCase(
    private val movieRepository: DefaultMovieRepository,
    private val dispatcher: CoroutineDispatcher
) : UseCase {
    operator fun invoke(): Flow<RestResult<MoviesWithGroup>> {
        return channelFlow {
            coroutineScope {
                launch {
                    send(
                        movieRepository.getMovies(
                            sortPairList[0].first,
                            sortPairList[0].second
                        )
                    )
                }
                launch {
                    send(
                        movieRepository.getMovies(
                            sortPairList[1].first,
                            sortPairList[1].second
                        )
                    )
                }
                launch {
                    send(
                        movieRepository.getMovies(
                            sortPairList[2].first,
                            sortPairList[2].second
                        )
                    )
                }
                launch {
                    send(
                        movieRepository.getMovies(
                            sortPairList[3].first,
                            sortPairList[3].second
                        )
                    )
                }
            }

        }.buildFlow(dispatcher)
    }
}

private val sortPairList: List<Pair<String, String>> = listOf(
    "revenue.desc" to "Revenue",
    "primary_release_date.asc" to "Release Date",
    "popularity.desc" to "Popular",
    "vote_average.asc" to "Top Rated"
)