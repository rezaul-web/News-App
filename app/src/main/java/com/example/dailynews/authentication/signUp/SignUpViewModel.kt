package com.example.dailynews.authentication.signUp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dailynews.utils.UiState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<FirebaseUser?>>(UiState.Initial)
    val uiState: StateFlow<UiState<FirebaseUser?>> = _uiState

    // Sign up method
    fun signUpWithEmailPassword(email: String, password: String) {
        _uiState.value = UiState.Loading
        viewModelScope.launch {
            try {
                firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _uiState.value = UiState.Success(firebaseAuth.currentUser)
                    } else {
                        _uiState.value = UiState.Error(task.exception?.message ?: "Sign-up failed")
                    }
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.message ?: "An error occurred")
            }
        }
    }
}
