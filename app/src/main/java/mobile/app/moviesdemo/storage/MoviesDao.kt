package mobile.app.moviesdemo.storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow
import mobile.app.data.ContainerParentEntity
import mobile.app.data.MovieEntity
import mobile.app.data.MovieWithGroupEntity

@Dao
interface MoviesDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovieGroup(group: MovieWithGroupEntity)


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovies(movies: List<MovieEntity>)

    @Transaction
    @Query("SELECT * FROM movie WHERE group_key = :groupKey")
    fun getMoviesByGroup(groupKey: String): Flow<List<MovieEntity>>


    @Transaction
    @Query("SELECT * FROM movie_group")
    fun getAllMoviesWithGroups(): Flow<List<ContainerParentEntity>>
}
