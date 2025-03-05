package mobile.app.data

import androidx.room.Embedded
import androidx.room.Relation

data class ContainerParentEntity(
    @Embedded val group: MovieWithGroupEntity,
    @Relation(
        parentColumn = "key",
        entityColumn = "group_id"
    )
    val movies: List<MovieEntity>
) {
    fun toMovieWithGroup(): MoviesWithGroup {
        return MoviesWithGroup(
            data = DiscoverResponse(
                page = group.page,
                totalPages = group.totalPages,
                totalResults = group.totalResults,
                results = movies.map {
                    it.toMovie()
                }
            ),
            title = group.title,
            key = group.key
        )
    }
}