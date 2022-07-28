package com.example.yelpapp.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.yelpapp.data.toError
import com.example.yelpapp.domain.Business
import com.example.yelpapp.domain.Error
import com.example.yelpapp.ui.detail.di.BusinessId
import com.example.yelpapp.usecases.GetBusinessByIdUseCase
import com.example.yelpapp.usecases.SwitchBusinessFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    @BusinessId private val id : String,
    private val getBusinessByIdUseCase: GetBusinessByIdUseCase,
    private val switchBusinessFavoriteUseCase: SwitchBusinessFavoriteUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(UiState())
    val state: StateFlow<UiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getBusinessByIdUseCase(id)
                .catch { cause -> _state.update { _state.value.copy(error = cause.toError()) } }
                .collect { business -> _state.update { UiState(business = business) } }
        }
    }

    fun onFavoriteClicked(){
        viewModelScope.launch {
            _state.value.business?.let { movie ->
                val error = switchBusinessFavoriteUseCase(movie)
                _state.update { it.copy(error = error) }
            }
        }
    }

    data class UiState(
        val loading: Boolean = false,
        val business: Business? = null,
        val error: Error? = null
    )
}

