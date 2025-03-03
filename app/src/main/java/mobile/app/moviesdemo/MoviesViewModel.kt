package mobile.app.moviesdemo

import mobile.app.usecase.GetMovieDetailUseCase
import mobile.app.usecase.GetMoviesUseCase
import javax.inject.Inject

class MoviesViewModel @Inject constructor(
    private val moviesUseCase: GetMoviesUseCase,
    private val movieDetailUseCase: GetMovieDetailUseCase
) : DefaultViewModel() {

    fun getMovies() {
        runFlow(moviesUseCase.execute(), onSuccess = {

        })
    }

    fun getMoviesDetail(movieId:Int){
        runFlow(movieDetailUseCase.execute(movieId), onSuccess = {

        })
    }
}