package com.example.dailynews.utils

sealed class UiState<out T> {
    data object Initial : UiState<Nothing>() // Represents the state before login
    data object Loading : UiState<Nothing>() // Represents loading state during login attempt
    data class Success<T>(val data: T) : UiState<T>() // Represents success state with data
    data class Error(val message: String) : UiState<Nothing>() // Represents error state with message
}


