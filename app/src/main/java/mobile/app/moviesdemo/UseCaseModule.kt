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
import mobile.app.usecase.GetMovieDetailUseCase
import mobile.app.usecase.GetMoviesUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Provides
    @Singleton
    fun provideMoviesUsecase(
       movieRepository: DefaultMovieRepository,
       dispatcher: CoroutineDispatcher
    ): GetMoviesUseCase {
        return GetMoviesUseCase(movieRepository,dispatcher)
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailUsecase(
        movieRepository: DefaultMovieRepository,
        dispatcher: CoroutineDispatcher
    ): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(movieRepository,dispatcher)
    }
}

