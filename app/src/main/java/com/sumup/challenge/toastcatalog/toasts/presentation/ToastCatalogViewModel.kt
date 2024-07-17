package com.sumup.challenge.toastcatalog.toasts.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import com.sumup.challenge.toastcatalog.toasts.domain.usecase.FetchToastCatalogUseCaseImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.sumup.challenge.toastcatalog.toasts.presentation.mapper.toToastUiModelList
import com.sumup.challenge.toastcatalog.toasts.presentation.model.ToastUiModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * toast catalog view model that is responsible of serving toasts to UI by invoking usecase
 * @param fetchToastCatalogUseCase the usecase which return toasts
 *
 */
@HiltViewModel
class ToastCatalogViewModel @Inject constructor(
    private val fetchToastCatalogUseCase: FetchToastCatalogUseCaseImpl
) : ViewModel() {

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading


    private val _toastCatalogState = MutableStateFlow<Result<List<ToastUiModel>>>(Result.success(
        emptyList()
    ))

    // Lazily initialize the StateFlow using stateIn so it won't start emitting values until a collector starts observing it.
    val toastCatalogState: StateFlow<Result<List<ToastUiModel>>> = _toastCatalogState
        .stateIn(viewModelScope, SharingStarted.Lazily, Result.success(emptyList()))
     fun fetchToastCatalog() {
        viewModelScope.launch {
            try {
                fetchToastCatalogUseCase.invoke().collect { toasts ->
                    _toastCatalogState.value = Result.success(toasts.toToastUiModelList())
                }
            } catch (e: Exception) {
                _toastCatalogState.value = Result.failure(e)
            } finally {
                _loading.value = false
            }
        }
    }
    fun retryLoadToasts() {
        fetchToastCatalog()
    }
}