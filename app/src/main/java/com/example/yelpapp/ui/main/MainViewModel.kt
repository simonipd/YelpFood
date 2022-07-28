package com.example.yelpapp.ui.main

import androidx.lifecycle.*
import com.example.yelpapp.data.toError
import com.example.yelpapp.domain.Business
import com.example.yelpapp.domain.Error
import com.example.yelpapp.usecases.GetBusinessUseCase
import com.example.yelpapp.usecases.RequestBusinessUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getBusinessUseCase: GetBusinessUseCase,
    private val requestBusinessUseCase: RequestBusinessUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getBusinessUseCase()
                .catch { cause -> _state.update { it.copy(error = cause.toError()) } }
                .collect{ bussiness -> _state.update { UiState(businesses = bussiness) }}
        }
    }

    fun onUiReady(){
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true)
            val error = requestBusinessUseCase()
            _state.value = _state.value.copy(loading = false, error = error)
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val businesses: List<Business>? = null,
        val error: Error? = null
    )
}
