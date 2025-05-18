package com.useapi.foodallergydetector.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface

import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.useapi.foodallergydetector.data.network.RetrofitClient
import com.useapi.foodallergydetector.data.store.TokenPreferences
import com.useapi.foodallergydetector.ui.auth.AuthViewModel
import com.useapi.foodallergydetector.ui.auth.AuthViewModelFactory
import com.useapi.foodallergydetector.ui.auth.LoginScreen
import com.useapi.foodallergydetector.ui.evaluate.EvaluateScreen
import com.useapi.foodallergydetector.ui.history.HistoryScreen
import com.useapi.foodallergydetector.ui.theme.FoodAllergyDetectorTheme

// extension property so you can do `this.dataStore` in any ComponentActivity
private val ComponentActivity.dataStore by preferencesDataStore("user_prefs")

class MainActivity : ComponentActivity() {

    // DataStore-backed token helper
    private val tokenPrefs by lazy { TokenPreferences(this) }

    // Creates AuthViewModel with the retrofit API + tokenPrefs
    private val authViewModel: AuthViewModel by viewModels {
       AuthViewModelFactory(
            api        = RetrofitClient.authApi,
             tokenPrefs = tokenPrefs
           )
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        // init RetrofitClient *before* anyone tries to use it
        val authViewModel: AuthViewModel by viewModels {
          AuthViewModelFactory(
                api        = RetrofitClient.authApi,
                tokenPrefs = tokenPrefs
               )
        }

        super.onCreate(savedInstanceState)

        setContent {
            FoodAllergyDetectorTheme {
                Surface {
                    val nav = rememberNavController()

                    NavHost(
                        navController = nav,
                        startDestination = Screen.Login.route
                    ) {
                        // LOGIN
                        composable(Screen.Login.route) {
                            LoginScreen(
                                viewModel      = authViewModel,
                                onLoginSuccess = { jwtToken ->
                                    // Here you have your JWT:
                                    // you could stash it in memory, pass to the next screen, etc.
                                    nav.navigate(Screen.Evaluate.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }

                        // EVALUATE
                        composable(Screen.Evaluate.route) {
                            EvaluateScreen(
                                tokenPrefs    = tokenPrefs,
                                onShowHistory = { nav.navigate(Screen.History.route) }
                            )
                        }

                        // HISTORY
                        composable(Screen.History.route) {
                            HistoryScreen()
                        }
                    }
                }
            }
        }
    }
}

// ----------------------------------------------------------------------------------
// Nav‐routes sealed class (at bottom of this file)
//
// Notice: no trailing commas, all braces balanced
// ----------------------------------------------------------------------------------
sealed class Screen(val route: String) {
    object Login    : Screen("login")
    object Evaluate : Screen("evaluate")
    object History  : Screen("history")
}
