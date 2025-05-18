// file: app/src/main/java/com/useapi/foodallergydetector/ui/auth/AuthViewModelFactory.kt
package com.useapi.foodallergydetector.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.useapi.foodallergydetector.data.network.AuthApi
import com.useapi.foodallergydetector.data.store.TokenPreferences

class AuthViewModelFactory(
    private val api: AuthApi,
    private val tokenPrefs: TokenPreferences
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(AuthViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return AuthViewModel(api, tokenPrefs) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}
