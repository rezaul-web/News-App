package com.example.dailynews.authentication.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.dailynews.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
@HiltViewModel
class LogInViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<FirebaseUser?>>(UiState.Initial)
    val uiState: StateFlow<UiState<FirebaseUser?>> = _uiState

    // Login method
    fun logInWithEmailPassword(email: String, password: String) {
        _uiState.value = UiState.Loading
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _uiState.value = UiState.Success(firebaseAuth.currentUser)
                } else {
                    _uiState.value = UiState.Error(task.exception?.message ?: "Login failed")
                }
            }
    }
}


