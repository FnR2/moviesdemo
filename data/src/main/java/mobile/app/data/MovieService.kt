package mobile.app.data

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieService {
    @GET("discover/movie")
    suspend fun discover(@Query("sort_by") sortBy: String): DiscoverResponse

    @GET("movie/{id}")
    suspend fun getMovieDetail(@Path("id") id: Long): MovieDetail

}