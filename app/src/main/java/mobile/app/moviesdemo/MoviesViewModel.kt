package mobile.app.moviesdemo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import mobile.app.usecase.GetMovieDetailUseCase
import mobile.app.usecase.GetMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: GetMoviesUseCase,
    private val movieDetailUseCase: GetMovieDetailUseCase
) : DefaultViewModel() {


    var popularMovies by mutableStateOf<List<String?>>(emptyList())
        private set

    var topRatedMovies by mutableStateOf<List<String?>>(emptyList())
        private set

    var revenueMovies by mutableStateOf<List<String?>>(emptyList())
        private set

    init {
        getMovies()
    }


    private fun getMovies() {
        runFlow(moviesUseCase.execute(), onSuccess = { response ->
            popularMovies = response.results.map {
                IMAGE_PREFIX.plus(it.posterPath)
            }
        })
    }

    fun getMoviesDetail(movieId: Int) {
        runFlow(movieDetailUseCase.execute(movieId), onSuccess = {

        })
    }
}