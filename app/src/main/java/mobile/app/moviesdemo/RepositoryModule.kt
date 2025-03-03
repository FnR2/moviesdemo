package mobile.app.moviesdemo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import mobile.app.data.DefaultMovieRepository
import mobile.app.data.MovieRepository
import mobile.app.data.MovieService
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {
    @Provides
    @Singleton
    fun provideMovieRepository(
        service: MovieService,
        dispatcher: CoroutineDispatcher
    ): DefaultMovieRepository {
        return MovieRepository(service, dispatcher)
    }

    @Provides
    @Singleton
    fun provideDispatcher() = Dispatchers.IO
}

