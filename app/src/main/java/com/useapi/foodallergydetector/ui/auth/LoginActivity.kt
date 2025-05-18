// file: app/src/main/java/com/useapi/foodallergydetector/ui/auth/LoginActivity.kt
package com.useapi.foodallergydetector.ui.auth

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels                        // ← make sure you have this
import androidx.compose.material3.Surface
import com.useapi.foodallergydetector.data.network.RetrofitClient
import com.useapi.foodallergydetector.data.store.TokenPreferences
import com.useapi.foodallergydetector.ui.evaluate.EvaluateActivity
import com.useapi.foodallergydetector.ui.theme.FoodAllergyDetectorTheme

class LoginActivity : ComponentActivity() {

    // Always use `by lazy` with the Activity context
    private val tokenPrefs by lazy { TokenPreferences(this) }

    private val authViewModel: AuthViewModel by viewModels {
        AuthViewModelFactory(RetrofitClient.authApi, tokenPrefs)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Make sure to initialize Retrofit before anything else!
        RetrofitClient.init(
            applicationContext,
            tokenPrefs = TODO()
        )

        setContent {
            FoodAllergyDetectorTheme {
                Surface {
                    LoginScreen(
                        viewModel = authViewModel,
                        onLoginSuccess = { jwtToken ->
                            val intent = Intent(this@LoginActivity, EvaluateActivity::class.java)
                            intent.putExtra("EXTRA_JWT", jwtToken)
                            startActivity(intent)
                            finish()
                        }
                    )
                }
            }
        }
    }
}
