package mobile.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

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
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "groupKey", index = true)
    val key: String
)