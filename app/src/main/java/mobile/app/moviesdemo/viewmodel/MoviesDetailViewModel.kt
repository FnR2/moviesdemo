package mobile.app.moviesdemo.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mobile.app.moviesdemo.Mapper
import mobile.app.usecase.GetMovieDetailUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesDetailViewModel @Inject constructor(
    private val movieDetailUseCase: GetMovieDetailUseCase,
    private val mapper: Mapper
) : DefaultViewModel() {


    private val _movieDetailState =
        MutableStateFlow<MovieDetailState>(MovieDetailState.InitialState(empty()))
    val movieDetailState: StateFlow<MovieDetailState> = _movieDetailState


    fun getMoviesDetail(movieId: Long) {
        executeUseCase(movieDetailUseCase(movieId), onSuccess = { response ->
            _movieDetailState.value = MovieDetailState.DataState(mapper.mapMovieDetail(response))
        })
    }

    private fun empty(): MovieDetailUIModel {
        return MovieDetailUIModel(0, ",", ",", ",", "", "")
    }

    private val _currentPosition = MutableStateFlow(0L)
    val currentPosition: StateFlow<Long> = _currentPosition

    fun updatePosition(position: Long) {
        _currentPosition.value = position
    }
}

data class MovieDetailUIModel(
    val id: Long,
    val title: String,
    val overView: String,
    val dateRelease: String,
    val voteAverage: String,
    val imagePath: String?,
    val streamSource: String = "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd"
) {

}

sealed class MovieDetailState(
    open val movie: MovieDetailUIModel
) {
    data class DataState(override val movie: MovieDetailUIModel) : MovieDetailState(movie)
    data class InitialState(override val movie: MovieDetailUIModel) : MovieDetailState(movie)

}

