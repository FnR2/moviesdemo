package mobile.app.moviesdemo.viewmodel

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import mobile.app.moviesdemo.Mapper
import mobile.app.usecase.GetMovieDetailUseCase
import mobile.app.usecase.GetMoviesByCategoryUseCase
import mobile.app.usecase.GetMoviesUseCase
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val moviesUseCase: GetMoviesUseCase,
    private val moviesByCategoriesUseCase: GetMoviesByCategoryUseCase,
    private val movieDetailUseCase: GetMovieDetailUseCase,
    private val mapper: Mapper
) : DefaultViewModel() {


    private val _moviesState =
        MutableStateFlow<MoviesState>(MoviesState(mutableListOf<DiscoverUIModel>()))
    val moviesState: StateFlow<MoviesState> = _moviesState


    init {
        getInitialMovies()
    }


    private fun getInitialMovies() {
        executeUseCase(moviesUseCase(), onSuccess = { response ->
            _moviesState.update { currentState ->
                val newList = currentState.list.toMutableList()
                newList.add(mapper.mapDiscoverMovies(response))
                currentState.copy(list = newList)
            }
        })
    }

    fun paginateMovies(key: String, index: Int, size: Int) {
        val shouldPaginate = (size - index) < PAGING_THRESHOLD
        if (!shouldPaginate) return

        _moviesState.value.list.firstOrNull { it.key == key }?.let { category ->
            if (category.isPaginating) return
            val nextPage = category.currentPage + 1

            _moviesState.update { currentState ->
                val updatedList = currentState.list.map {
                    if (it.key == key) it.copy(isPaginating = true) else it
                }.toMutableList()
                currentState.copy(list = updatedList)
            }


            executeUseCase(
                moviesByCategoriesUseCase(key = key, page = nextPage),
                onSuccess = { response ->
                    val newMovies = mapper.mapMoviesByCategory(response.data.results)

                    _moviesState.update { currentState ->
                        val updatedList = currentState.list.map { category ->
                            if (category.key == key) {
                                category.copy(
                                    currentPage = nextPage,
                                    movieList = (category.movieList + newMovies).toMutableList(),
                                    isPaginating = false
                                )
                            } else {
                                category
                            }
                        }.toMutableList()

                        currentState.copy(list = updatedList)
                    }
                }
            )
        }

    }


}

data class MoviesState(val list: MutableList<DiscoverUIModel>)
data class DiscoverUIModel(
    val currentPage: Long,
    val title: String,
    val key: String,
    var movieList: MutableList<MovieDetailUIModel>,
    var isPaginating: Boolean = false
)

const val INTENT_PARAM = "movieId"
const val PAGING_THRESHOLD = 5