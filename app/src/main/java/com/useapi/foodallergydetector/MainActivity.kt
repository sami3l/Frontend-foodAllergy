package com.useapi.foodallergydetector.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
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

// extension property for DataStore
private val ComponentActivity.dataStore by preferencesDataStore("user_prefs")

class MainActivity : ComponentActivity() {

    // DataStore-backed token helper
    private val tokenPrefs by lazy { TokenPreferences(this) }

    // <-- we DO NOT touch this delegate; it only creates the ViewModel when first needed
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(RetrofitClient.authApi, tokenPrefs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // ① Initialize RetrofitClient **before** anything else tries to use it
        RetrofitClient.init(applicationContext)

        super.onCreate(savedInstanceState)

        setContent {
            FoodAllergyDetectorTheme {
                Surface {
                    val nav = rememberNavController()

                    NavHost(navController = nav, startDestination = Screen.Login.route) {
                        composable(Screen.Login.route) {
                            LoginScreen(
                                viewModel      = authViewModel,
                                onLoginSuccess = { jwtToken ->
                                    // you now have the raw JWT in `jwtToken`
                                    // you can stash it in memory, or navigate & pass it on
                                    nav.navigate(Screen.Evaluate.route) {
                                        popUpTo(Screen.Login.route) { inclusive = true }
                                    }
                                }
                            )
                        }
                        composable(Screen.Evaluate.route) {
                            EvaluateScreen(
                                onShowHistory = { nav.navigate(Screen.History.route) }
                            )
                        }
                        composable(Screen.History.route) {
                            HistoryScreen()
                        }
                    }
                }
            }
        }
    }
}

// bottom of file: your Nav‐routes sealed class
sealed class Screen(val route: String) {
    object Login    : Screen("login")
    object Evaluate : Screen("evaluate")
    object History  : Screen("history")
}
