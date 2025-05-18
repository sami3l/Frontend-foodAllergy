
package com.useapi.foodallergydetector

import android.app.Application
import com.useapi.foodallergydetector.data.network.RetrofitClient
import com.useapi.foodallergydetector.data.store.TokenPreferences


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        val tokenPrefs = TokenPreferences(this)
        RetrofitClient.init(this, tokenPrefs)
    }
}