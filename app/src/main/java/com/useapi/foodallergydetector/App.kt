
package com.useapi.foodallergydetector

import android.app.Application
import com.useapi.foodallergydetector.data.network.RetrofitClient


class App : Application() {
    override fun onCreate() {
        super.onCreate()
        // ☝️ this happens before ANY Activity or ViewModel tries to touch RetrofitClient.authApi
        RetrofitClient.init(this)
    }
}