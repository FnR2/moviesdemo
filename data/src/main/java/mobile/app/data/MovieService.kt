package mobile.app.data

import retrofit2.http.*

interface MovieService {
    @GET("https://api.themoviedb.org/3/discover/movie")
    suspend fun discover(@QueryMap params: Map<String, String>): RestResponse<DiscoverResponse>

    @GET("https://api.themoviedb.org/3/discover/movie/{id}")
    suspend fun getMovieDetail(@Path("id") id:Int): RestResponse<MovieDetail>

}