package mobile.app.moviesdemo.storage

import kotlinx.coroutines.flow.Flow
import mobile.app.data.ContainerParentEntity
import mobile.app.data.DefaultLocalMovieRepository
import mobile.app.data.MovieEntity

class LocalRepository(
    private val dao: MoviesDao
) : DefaultLocalMovieRepository {
    override suspend fun insertMovies() {
        TODO("Not yet implemented")
    }

    override suspend fun getMovies(groupKey: String): Flow<List<MovieEntity>> {
        return dao.getMoviesByGroup(groupKey)
    }

    override suspend fun getAllMovieWithCategories(): Flow<List<ContainerParentEntity>> {
        return dao.getAllMoviesWithGroups()
    }
}