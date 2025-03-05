package mobile.app.data

import kotlinx.coroutines.flow.Flow

interface DefaultLocalMovieRepository {
    suspend fun insertMovieWithGroup(movieWithGroupEntity: MovieWithGroupEntity)
    suspend fun insertMovies(movies: List<MovieEntity>)
    suspend fun getMovies(groupKey: String): Flow<List<MovieEntity>>
    suspend fun getAllMovieWithCategories(): Flow<List<ContainerParentEntity>>
}