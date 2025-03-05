package mobile.app.moviesdemo.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import mobile.app.data.MovieEntity


@Database(
    version = 1, entities = [
        MovieEntity::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao
}
