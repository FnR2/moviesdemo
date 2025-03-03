package mobile.app.data

interface DefaultMovieRepository {
    suspend fun getMovies(parameters:Map<String,String>):RestResult<DiscoverResponse>
    suspend fun getMovieDetail(movieId:Int):RestResult<MovieDetail>
}