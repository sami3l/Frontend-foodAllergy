package com.useapi.foodallergydetector.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Surface
import com.useapi.foodallergydetector.data.network.RetrofitClient
import com.useapi.foodallergydetector.data.store.TokenPreferences
import com.useapi.foodallergydetector.ui.evaluate.EvaluateActivity
import com.useapi.foodallergydetector.ui.evaluate.EvaluateScreen
import com.useapi.foodallergydetector.ui.theme.FoodAllergyDetectorTheme

class LoginActivity : ComponentActivity() {
    private val tokenPrefs by lazy { TokenPreferences(this) }
    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(RetrofitClient.authApi, tokenPrefs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        RetrofitClient.init(applicationContext)

        setContent {
            FoodAllergyDetectorTheme {
                Surface {
                    LoginScreen(
                        viewModel       = authViewModel,
                        onLoginSuccess = { jwtToken ->
                            // 1) create an Intent to your EvaluateActivity
                            val intent = Intent(this@LoginActivity, EvaluateActivity::class.java).apply {
                                // if you need to pass the token along:
                                putExtra("EXTRA_JWT", jwtToken)
                            }
                            // 2) launch it
                            startActivity(intent)
                            // 3) optionally finish() so back‐stack won’t return to login
                            finish()
                        }
                    )
                }
            }
        }
    }
}
