package mobile.app.usecase

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import mobile.app.data.ContainerParentEntity
import mobile.app.data.DefaultConnectionChecker
import mobile.app.data.DefaultLocalMovieRepository
import mobile.app.data.DefaultMovieRepository
import mobile.app.data.DiscoverResponse
import mobile.app.data.MoviesWithGroup
import mobile.app.data.RestResult

class GetMoviesUseCase(
    private val movieRepository: DefaultMovieRepository,
    private val dispatcher: CoroutineDispatcher,
    private val connectionChecker: DefaultConnectionChecker,
    private val localMovieRepository: DefaultLocalMovieRepository
) : UseCase {
    operator fun invoke(): Flow<RestResult<MoviesWithGroup>> = flow {
        if (connectionChecker.isConnectionAvailable()) {
            val channel = Channel<RestResult<MoviesWithGroup>>(capacity = Channel.UNLIMITED)
            coroutineScope {
                sortPairList.entries.forEachIndexed { _, entry ->
                    launch {
                        val result = movieRepository.getMovies(entry.key, entry.value)
                        channel.send(result)

                        if (result is RestResult.Success) {
                            saveLocally(result, entry.key)
                        }
                    }
                }
            }

            channel.close()
            for (result in channel) {
                emit(result)
            }
        } else {
            getFromLocal().flowOn(dispatcher).collect { localMovies ->
                localMovies.map { it.toMovieWithGroup() }
                    .forEach { movieGroup ->
                        emit(RestResult.Success(movieGroup))
                    }
            }
        }

    }.buildFlow(dispatcher)

    private suspend fun saveLocally(result: RestResult.Success<MoviesWithGroup>, key: String) {
        val movieGroup = result.data.toMovieWithGroupEntity()
        val movieList = result.data.data.results.map { it.toMovieEntity(key) }
        localMovieRepository.insertMovieWithGroup(movieGroup)
        localMovieRepository.insertMovies(movieList)

    }

    private suspend fun getFromLocal(): Flow<List<ContainerParentEntity>> {
        return localMovieRepository.getAllMovieWithCategories()
    }
}

val sortPairList: Map<String, String> = mapOf(
    "revenue.desc" to "Revenue",
    "primary_release_date.asc" to "Release Date",
    "popularity.desc" to "Popular",
    "vote_average.asc" to "Top Rated"
)