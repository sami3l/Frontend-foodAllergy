// app/src/main/java/com/useapi/foodallergydetector/data/network/RetrofitClient.kt
package com.useapi.foodallergydetector.data.network

import android.content.Context
import com.useapi.foodallergydetector.data.store.TokenPreferences
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitClient {
    private lateinit var _authApi: AuthApi
    private lateinit var _scanApi: ScanApi
    val authApi: AuthApi get() = _authApi
    val scanApi: ScanApi get() = _scanApi

    fun init(appContext: Context, tokenPrefs: TokenPreferences) {
        val client = OkHttpClient.Builder()
            .addInterceptor(JwtInterceptor(tokenPrefs))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.11.128:8082/")   // see step 4
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        _authApi = retrofit.create(AuthApi::class.java)
        _scanApi = retrofit.create(ScanApi::class.java)
    }
}