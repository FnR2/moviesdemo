package mobile.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_group",
)
data class MovieWithGroupEntity(
    @ColumnInfo(name = "page")
    val page: Long,
    @ColumnInfo(name = "total_pages")
    val totalPages: Long,
    @ColumnInfo(name = "total_results")
    val totalResults: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "key")
    val groupKey: String
)