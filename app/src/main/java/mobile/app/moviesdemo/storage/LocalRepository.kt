package mobile.app.moviesdemo.storage

import kotlinx.coroutines.flow.Flow
import mobile.app.data.ContainerParentEntity
import mobile.app.data.DefaultLocalMovieRepository
import mobile.app.data.MovieEntity
import mobile.app.data.MovieWithGroupEntity

class LocalRepository(
    private val dao: MoviesDao
) : DefaultLocalMovieRepository {
    override suspend fun insertMovieWithGroup(movieWithGroupEntity: MovieWithGroupEntity) {
        dao.insertMovieGroup(movieWithGroupEntity)
    }

    override suspend fun insertMovies(movies: List<MovieEntity>) {
        dao.insertMovies(movies)
    }

    override suspend fun getMovies(groupKey: String): Flow<List<MovieEntity>> {
        return dao.getMoviesByGroup(groupKey)
    }

    override suspend fun getAllMovieWithCategories(): Flow<List<ContainerParentEntity>> {
        return dao.getAllMoviesWithGroups()
    }
}