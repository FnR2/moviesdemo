package mobile.app.data

interface DefaultMovieRepository {
    suspend fun getMovies(sortBy: String,title: String): RestResult<MoviesWithGroup>
    suspend fun getMovieDetail(movieId: Int): RestResult<MovieDetail>
}