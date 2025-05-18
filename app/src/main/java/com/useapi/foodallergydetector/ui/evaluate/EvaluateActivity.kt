package com.useapi.foodallergydetector.ui.evaluate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.useapi.foodallergydetector.data.store.TokenPreferences

import com.useapi.foodallergydetector.ui.history.HistoryActivity
import com.useapi.foodallergydetector.ui.theme.FoodAllergyDetectorTheme


class EvaluateActivity : ComponentActivity() {

    private val tokenPrefs by lazy { TokenPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodAllergyDetectorTheme {
                EvaluateScreen(
                    tokenPrefs = tokenPrefs,
                    onShowHistory = {
                        // Launch HistoryActivity
                        startActivity(Intent(this, HistoryActivity::class.java))
                    }
                )
            }
        }
    }
}

