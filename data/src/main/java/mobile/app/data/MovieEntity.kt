package mobile.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index

@Entity(
    tableName = "movie",
    foreignKeys = [
        ForeignKey(
            entity = MovieWithGroupEntity::class,
            parentColumns = ["key"],
            childColumns = ["group_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("group_id")]
)
data class MovieEntity(
    @ColumnInfo("poster_path")
    val posterPath: String,
    @ColumnInfo("_id")
    val id: Long,
    @ColumnInfo("vote_average")
    val voteAverage: Double,
    @ColumnInfo("release_date")
    val releaseDate: String?,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("overview")
    val overview: String,
) {
    fun toMovie(): Movie {
        return Movie(
            posterPath = posterPath,
            id = id,
            voteAverage = voteAverage,
            releaseDate = releaseDate,
            title = title,
            overview = overview
        )
    }
}
