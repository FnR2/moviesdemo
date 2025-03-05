package mobile.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "movie_group")
data class MovieWithGroupEntity(
    @ColumnInfo("page")
    val page: Long,
    @ColumnInfo("total_pages")
    val totalPages: Long,
    @ColumnInfo("results")
    val totalResults: Long,
    @ColumnInfo("title")
    val title: String,
    @ColumnInfo("groupKey")
    val key: String
)