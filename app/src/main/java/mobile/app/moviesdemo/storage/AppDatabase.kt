package mobile.app.moviesdemo.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import mobile.app.data.MovieEntity
import mobile.app.data.MovieWithGroupEntity

@Database(
    version = 1,
    entities = [
        MovieEntity::class,
        MovieWithGroupEntity::class,
    ],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}
