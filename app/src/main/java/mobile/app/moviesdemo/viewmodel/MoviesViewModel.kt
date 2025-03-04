package mobile.app.moviesdemo.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import mobile.app.moviesdemo.Mapper
import mobile.app.usecase.GetMovieDetailUseCase
import mobile.app.usecase.GetMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: GetMoviesUseCase,
    private val movieDetailUseCase: GetMovieDetailUseCase,
    private val mapper: Mapper
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
                newList.add(mapper.mapMovies(response))
                currentState.copy(list = newList)
            }
        })
    }


}

data class MoviesState(val list: MutableList<DiscoverUIModel>)
data class DiscoverUIModel(val title: String, val movieList: List<MovieUIModel>)
data class MovieUIModel(val id: Long, val imagePath: String?)

const val INTENT_PARAM = "movieId"
