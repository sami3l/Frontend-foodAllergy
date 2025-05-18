package com.useapi.foodallergydetector.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.useapi.foodallergydetector.data.model.AuthRequest
import com.useapi.foodallergydetector.data.network.AuthApi
import com.useapi.foodallergydetector.data.store.TokenPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/** Represents the current login UI state */
sealed class AuthState {
    object Idle    : AuthState()
    object Loading : AuthState()
    data class Success(val token: String) : AuthState()
    data class Error  (val message: String) : AuthState()
}

class AuthViewModel(
    private val api: AuthApi,
    private val tokenPrefs: TokenPreferences
) : ViewModel() {

    private val _state = MutableStateFlow<AuthState>(AuthState.Idle)
    val state: StateFlow<AuthState> = _state

    /** Kick off a login request */
    fun login(username: String, password: String) {
        if (username.isBlank() || password.isBlank()) {
            _state.value = AuthState.Error("Username and password must not be empty")
            return
        }

        viewModelScope.launch {
            _state.value = AuthState.Loading
            try {
                val resp = api.login(AuthRequest(username, password))
                val token = resp.jwt
                if (token.isNullOrEmpty()) {
                    _state.value = AuthState.Error("Login succeeded but no token returned")
                } else {
                    // persist it to DataStore
                    tokenPrefs.saveToken(token)
                    // emit success along with the raw JWT
                    _state.value = AuthState.Success(token)
                }
            } catch (t: Throwable) {
                _state.value = AuthState.Error(t.localizedMessage ?: "Login failed")
            }
        }
    }
}