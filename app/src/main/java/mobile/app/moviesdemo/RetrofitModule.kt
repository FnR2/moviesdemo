package mobile.app.moviesdemo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import mobile.app.data.HeaderInterceptor
import mobile.app.data.RetrofitClient
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Singleton
    @Provides
    fun provideRetrofitClient(
        baseUrl: String,
        client: OkHttpClient
    ): RetrofitClient {
        return RetrofitClient(baseUrl, client)
    }

    @Singleton
    @Provides
    fun provideRetrofit(retrofitClient: RetrofitClient): Retrofit {
        return retrofitClient.create()
    }


    @Singleton
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
        logger: HttpLoggingInterceptor,
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(headerInterceptor)
        builder.addInterceptor(logger)
        builder.readTimeout(30, TimeUnit.SECONDS)
        builder.writeTimeout(30, TimeUnit.SECONDS)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRestUrl() = "https://api.themoviedb.org/3/discover/"


    @Singleton
    @Provides
    fun providesApiHeaders(
    ): List<Pair<String, String>> {
        val headerList = arrayListOf(
            Pair("Accept", "application/json; charset=utf-8"),
            Pair("Content-Type", "application/json"),
            Pair(AUTHORIZATION, BEARER.plus(" ").plus(TOKEN))
        )
        return headerList
    }


    @Singleton
    @Provides
    fun provideHeaderInterceptor(
        headers: List<Pair<String, String>>,
    ): HeaderInterceptor {
        return HeaderInterceptor(headers)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        return logging
    }
}

const val AUTHORIZATION = "Authorization"
const val BEARER = "Bearer"
const val TOKEN =
    "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI1MzYxZWE4YmM4YWUxMWU0MzE3N2ViNmE1MjQ0ODc1NSIsIm5iZiI6MTc0MDk5MTMyNC4zMzEsInN1YiI6IjY3YzU2YjVjNTY0ZDI1NzVkOTkxZmU2ZSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.qlxEltDX9hWYtOOnFjKIc8uVpDYB-iCeZDLeLeQxQck"
const val IMAGE_PREFIX = "https://image.tmdb.org/t/p/w500"