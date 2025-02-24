package com.ardondev.noteskmp.ui

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class UiStateHandler<T> {
    private val _uiState = MutableStateFlow<UiState<T>>(UiState.Loading)
    val uiState: StateFlow<UiState<T>> = _uiState.asStateFlow()

    fun setLoading() {
        _uiState.value = UiState.Loading
    }

    fun setSuccess(data: T) {
        _uiState.value = UiState.Success(data)
    }

    fun setError(message: String) {
        _uiState.value = UiState.Error(message)
    }
}