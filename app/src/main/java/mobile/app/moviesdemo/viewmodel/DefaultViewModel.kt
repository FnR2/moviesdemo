package mobile.app.moviesdemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import mobile.app.data.RestResult

abstract class DefaultViewModel : ViewModel() {

    fun <T> executeUseCase(
        result: Flow<RestResult<T>>,
        onSuccess: (suspend (T) -> Unit)
    ) {
        viewModelScope.launch {
            result.collectLatest { result ->
                when (result) {
                    is RestResult.Success -> {
                        onSuccess(result.data)
                    }

                    is RestResult.Failure -> {
                        handleError(result.exception)
                    }

                    is RestResult.Loading -> {
                        handleLoading(result.isShowing)
                    }
                }
            }
        }
    }

    private fun handleError(error: Throwable) {

    }

    open fun handleLoading(showLoading: Boolean) {

    }

}