package mobile.app.data

interface DefaultMovieRepository {
    suspend fun getMovies(sortBy: String,title: String,page:Long = 1): RestResult<MoviesWithGroup>
    suspend fun getMovieDetail(movieId: Long): RestResult<MovieDetail>
}