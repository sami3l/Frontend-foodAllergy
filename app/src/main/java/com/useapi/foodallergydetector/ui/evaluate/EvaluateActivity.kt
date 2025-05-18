package com.useapi.foodallergydetector.ui.evaluate

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.useapi.foodallergydetector.ui.history.HistoryActivity       // ← import HistoryActivity
import com.useapi.foodallergydetector.ui.theme.FoodAllergyDetectorTheme
import com.useapi.foodallergydetector.ui.evaluate.EvaluateScreen     // ← import the Composable

class EvaluateActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodAllergyDetectorTheme {
                EvaluateScreen(
                    onShowHistory = {
                        // This will now correctly resolve HistoryActivity
                        startActivity(Intent(this, HistoryActivity::class.java))
                    }
                )
            }
        }
    }
}
