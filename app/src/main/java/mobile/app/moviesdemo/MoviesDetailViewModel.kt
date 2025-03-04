package mobile.app.moviesdemo

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import mobile.app.usecase.GetMovieDetailUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesDetailViewModel @Inject constructor(
    private val movieDetailUseCase: GetMovieDetailUseCase,
    private val mapper: Mapper
) : DefaultViewModel() {


    private val _movieDetailState =
        MutableStateFlow<MovieDetailState>(MovieDetailState.InitialState)
    val movieDetailState: StateFlow<MovieDetailState> = _movieDetailState


    init {

    }

    fun getMoviesDetail(movieId: Long) {
        runFlow(movieDetailUseCase(movieId), onSuccess = { response ->
            _movieDetailState.value = MovieDetailState.DataState(mapper.mapMovieDetail(response))
        })
    }
}

data class MovieDetailUIModel(
    val id: Int,
    val title: String,
    val overView: String,
    val dateRelease: String,
    val voteAverage: String,
    val imagePath: String?,
    val streamSource: String = "https://storage.googleapis.com/wvmedia/cenc/h264/tears/tears.mpd"
)

sealed class MovieDetailState {
    data class DataState(val movie: MovieDetailUIModel) : MovieDetailState()
    data object InitialState : MovieDetailState()

}

