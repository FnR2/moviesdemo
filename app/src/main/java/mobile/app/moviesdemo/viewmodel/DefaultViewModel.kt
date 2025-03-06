package mobile.app.moviesdemo.viewmodel

import android.util.Log
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

    open fun handleError(error: Throwable) {
        Log.d(this::class.java.name, error.toString())
        // handle error in here
    }

    open fun handleLoading(showLoading: Boolean) {
        // we can show progress.This is an open so this can be overridden if needed.
    }
}
