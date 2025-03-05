package mobile.app.data

import kotlinx.coroutines.flow.Flow

interface DefaultLocalMovieRepository {
    suspend fun insertMovies()
    suspend fun getMovies(groupKey: String): Flow<List<MovieEntity>>
    suspend fun getAllMovieWithCategories(): Flow<List<ContainerParentEntity>>
}