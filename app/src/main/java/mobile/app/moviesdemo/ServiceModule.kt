package mobile.app.moviesdemo

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import mobile.app.data.DefaultConnectionChecker
import mobile.app.data.MovieService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    @Singleton
    @Provides
    fun provideMovieService(retrofit: Retrofit): MovieService {
        return retrofit.create(MovieService::class.java)
    }


    @Singleton
    @Provides
    fun provideConnectionChecker(@ApplicationContext context: Context): DefaultConnectionChecker {
        return ConnectionChecker(context)
    }
}