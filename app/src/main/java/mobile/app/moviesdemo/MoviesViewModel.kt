package mobile.app.moviesdemo

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import mobile.app.data.MoviesWithGroup
import mobile.app.usecase.GetMovieDetailUseCase
import mobile.app.usecase.GetMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: GetMoviesUseCase,
    private val movieDetailUseCase: GetMovieDetailUseCase
) : DefaultViewModel() {


    private val _moviesState =
        MutableStateFlow<MoviesState>(MoviesState(mutableListOf<DiscoverUIModel>()))
    val moviesState: StateFlow<MoviesState> = _moviesState

    init {
        getMovies()
    }


    private fun getMovies() {
        runFlow(moviesUseCase(), onSuccess = { response ->
            _moviesState.update { currentState ->
                val newList = currentState.list.toMutableList()
                newList.add(map(response))
                currentState.copy(list = newList)
            }
        })
    }

    fun getMoviesDetail(movieId: Int) {
        runFlow(movieDetailUseCase(movieId), onSuccess = {

        })
    }

    private fun map(model: MoviesWithGroup): DiscoverUIModel {
        return DiscoverUIModel(title = model.title, movieList = model.data.results.map {
            MovieUIModel(id = it.id, imagePath = mapImagePath(it.posterPath))
        })
    }

    private fun mapImagePath(url: String?): String? {
        return url?.let { IMAGE_PREFIX.plus(it) }
    }
}

data class MoviesState(val list: MutableList<DiscoverUIModel>)
data class DiscoverUIModel(val title: String, val movieList: List<MovieUIModel>)
data class MovieUIModel(val id: Long, val imagePath: String?)
