package mobile.app.moviesdemo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import mobile.app.data.DefaultConnectionChecker
import mobile.app.data.DefaultLocalMovieRepository
import mobile.app.data.DefaultMovieRepository
import mobile.app.usecase.GetMovieDetailUseCase
import mobile.app.usecase.GetMoviesByCategoryUseCase
import mobile.app.usecase.GetMoviesUseCase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {
    @Provides
    @Singleton
    fun provideMoviesUseCase(
        movieRepository: DefaultMovieRepository,
        dispatcher: CoroutineDispatcher,
        connectionChecker: DefaultConnectionChecker,
        localMovieRepository: DefaultLocalMovieRepository
    ): GetMoviesUseCase {
        return GetMoviesUseCase(
            movieRepository,
            dispatcher,
            connectionChecker,
            localMovieRepository
        )
    }

    @Provides
    @Singleton
    fun provideGetMovieDetailUseCase(
        movieRepository: DefaultMovieRepository,
        dispatcher: CoroutineDispatcher
    ): GetMovieDetailUseCase {
        return GetMovieDetailUseCase(movieRepository, dispatcher)
    }

    @Provides
    @Singleton
    fun providePaginateUseCase(
        movieRepository: DefaultMovieRepository,
        dispatcher: CoroutineDispatcher
    ): GetMoviesByCategoryUseCase {
        return GetMoviesByCategoryUseCase(movieRepository, dispatcher)
    }

    @Provides
    @Singleton
    fun provideMapper() = Mapper()
}
