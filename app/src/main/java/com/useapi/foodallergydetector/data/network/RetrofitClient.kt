// app/src/main/java/com/useapi/foodallergydetector/data/network/RetrofitClient.kt
package com.useapi.foodallergydetector.data.network

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// if you haven’t already, add this top‐level extension:
private val Context.dataStore by preferencesDataStore("user_prefs")

object RetrofitClient {

    // will be initialized in App.onCreate()
    private lateinit var retrofit: Retrofit

    /**  Must call before any `.authApi` or `.scanApi` usage  */
    fun init(context: Context, baseUrl: String = "http://192.168.11.128:8082/") {
        val client = OkHttpClient.Builder()
            .addInterceptor(JwtInterceptor(context.dataStore))
            .build()

        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /** only built when you actually call it (and after init) */
    val authApi: AuthApi
        get() = retrofit.create(AuthApi::class.java)

    /** only built when you actually call it (and after init) */
    val scanApi: ScanApi
        get() = retrofit.create(ScanApi::class.java)
}
