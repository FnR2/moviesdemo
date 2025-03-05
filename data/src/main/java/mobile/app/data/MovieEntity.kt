package mobile.app.data

import androidx.room.*

@Entity(
    tableName = "movie",
    foreignKeys = [
        ForeignKey(
            entity = MovieWithGroupEntity::class,
            parentColumns = ["key"],
            childColumns = ["group_key"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class MovieEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "poster_path") val posterPath: String,
    @ColumnInfo(name = "vote_average") val voteAverage: Double,
    @ColumnInfo(name = "release_date") val releaseDate: String?,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "overview") val overview: String,
    @ColumnInfo(name = "group_key") val groupKey: String
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
