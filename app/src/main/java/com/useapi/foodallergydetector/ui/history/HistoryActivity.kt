package com.useapi.foodallergydetector.ui.history

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.useapi.foodallergydetector.ui.theme.FoodAllergyDetectorTheme

class HistoryActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodAllergyDetectorTheme {
                HistoryScreen()
            }
        }
    }
}
