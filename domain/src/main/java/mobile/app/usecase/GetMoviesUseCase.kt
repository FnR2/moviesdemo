package mobile.app.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import mobile.app.data.DefaultConnectionChecker
import mobile.app.data.DefaultLocalMovieRepository
import mobile.app.data.DefaultMovieRepository
import mobile.app.data.MoviesWithGroup
import mobile.app.data.RestResult

class GetMoviesUseCase(
    private val movieRepository: DefaultMovieRepository,
    private val dispatcher: CoroutineDispatcher,
    private val connectionChecker: DefaultConnectionChecker,
    private val localMovieRepository: DefaultLocalMovieRepository
) : UseCase {
    operator fun invoke(): Flow<RestResult<MoviesWithGroup>> = flow {
        val channel = Channel<RestResult<MoviesWithGroup>>(capacity = Channel.UNLIMITED)
        coroutineScope {
            sortPairList.entries.forEachIndexed { _, entry ->
                launch {
                    val result = movieRepository.getMovies(entry.key, entry.value)
                    channel.send(result)
                    if (result is RestResult.Success) {
                        with(result.data) {
                            localMovieRepository.insertMovieWithGroup(toMovieWithGroupEntity())
                            localMovieRepository.insertMovies(data.results.map {
                                it.toMovieEntity(entry.key)
                            })
                        }
                    }

                }
            }
        }

        for (result in channel) {
            emit(result)
        }
    }.buildFlow(dispatcher)
}

val sortPairList: Map<String, String> = mapOf(
    "revenue.desc" to "Revenue",
    "primary_release_date.asc" to "Release Date",
    "popularity.desc" to "Popular",
    "vote_average.asc" to "Top Rated"
)