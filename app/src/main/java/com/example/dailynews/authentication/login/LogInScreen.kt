package com.example.dailynews.authentication.login

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.dailynews.utils.UiState

@Composable
fun LogInScreen(
    viewModel: LogInViewModel = hiltViewModel(),
    modifier: Modifier = Modifier,
    navController: NavController
) {
    // Observe the UI state
    val uiState by viewModel.uiState.collectAsState()

    // Local state for email, password, and password visibility
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isPasswordVisible by remember { mutableStateOf(false) }

    // Handle different UI states: Loading, Success, Error


    // Login form UI
    Column(modifier = modifier.padding(16.dp)) {
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Email)
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { isPasswordVisible = !isPasswordVisible }) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "Toggle password visibility"
                    )
                }
            }
        )
        val context= LocalContext.current
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                // Trigger the login function in the viewModel
                viewModel.logInWithEmailPassword(email, password)
                val state=uiState

                when (state) {
                    is UiState.Loading -> {

                    }

                    is UiState.Error -> {
                        Toast.makeText(context, state.message,Toast.LENGTH_SHORT).show()
                    }
                    UiState.Initial -> {

                    }
                    is UiState.Success -> {
                       navController.navigate("home")
                        Toast.makeText(context, "Login Successful", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Log In")
        }

    }
}
