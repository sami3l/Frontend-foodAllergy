package com.useapi.foodallergydetector.data.store


import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class TokenPreferences(private val context: Context) {

    companion object {
        private val JWT_KEY = stringPreferencesKey("jwt_token")
    }

    /** Flow that emits the saved JWT or null */
    val jwtToken: Flow<String?> = context.dataStore.data
        .map { prefs -> prefs[JWT_KEY] }

    /** Persist the JWT */
    suspend fun saveToken(token: String) {
        context.dataStore.edit { prefs ->
            prefs[JWT_KEY] = token
        }
    }

    /** Clear on logout */
    suspend fun clearToken() {
        context.dataStore.edit { prefs ->
            prefs.remove(JWT_KEY)
        }
    }
}
