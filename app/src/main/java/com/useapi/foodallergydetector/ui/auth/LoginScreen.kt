// file: ui/auth/LoginScreen.kt
package com.useapi.foodallergydetector.ui.auth

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.flow.StateFlow

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoginSuccess: (String) -> Unit
)  {
    var username by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val state by viewModel.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Please log in", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { viewModel.login(username.trim(), password) },
            enabled = state != AuthState.Loading,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Login")
        }
        Spacer(Modifier.height(16.dp))

        when (val s = state) {
            is AuthState.Idle    -> { /* show nothing special */ }
            is AuthState.Loading -> { CircularProgressIndicator() }
            is AuthState.Error   -> { Text(s.message, color = Color.Red) }
            is AuthState.Success -> {
                // once we see a token, invoke the callback:
                // (and maybe reset viewModel back to Idle if you want)
                onLoginSuccess(s.token)
            }
    }
}
}
