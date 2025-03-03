package mobile.app.data

import retrofit2.http.*

interface MovieService {
    @GET("discover/movie")
    suspend fun discover(@Query("sort_by") sortBy: String): DiscoverResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Long): MovieDetail

}