// YApi QuickType插件生成，具体参考文档:https://plugins.jetbrains.com/plugin/18847-yapi-quicktype/documentation

package mobile.app.data

data class MovieDetail (
    val originalLanguage: String,
    val imdbID: String,
    val video: Boolean,
    val title: String,
    val backdropPath: String,
    val revenue: Long,
    val genres: List<Genre>,
    val popularity: Double,
    val productionCountries: List<ProductionCountry>,
    val id: Long,
    val voteCount: Long,
    val budget: Long,
    val overview: String,
    val originalTitle: String,
    val runtime: Long,
    val posterPath: String,
    val originCountry: List<String>,
    val spokenLanguages: List<SpokenLanguage>,
    val productionCompanies: List<ProductionCompany>,
    val releaseDate: String,
    val voteAverage: Double,
    val belongsToCollection: BelongsToCollection,
    val tagline: String,
    val adult: Boolean,
    val homepage: String,
    val status: String
)

data class BelongsToCollection (
    val backdropPath: String,
    val name: String,
    val id: Long,
    val posterPath: String
)

data class Genre (
    val name: String,
    val id: Long
)

data class ProductionCompany (
    val logoPath: String,
    val name: String,
    val id: Long,
    val originCountry: String
)

data class ProductionCountry (
    val iso3166_1: String,
    val name: String
)

data class SpokenLanguage (
    val name: String,
    val iso639_1: String,
    val englishName: String
)
