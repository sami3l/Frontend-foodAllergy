package com.useapi.foodallergydetector.data.network

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class JwtInterceptor(
    private val tokenProvider: DataStore<Preferences>
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val jwt = runBlocking { tokenProvider }  // small blocking call; OK here
        val builder = original.newBuilder()
        jwt?.let {
            builder.addHeader("Authorization", "Bearer $it")
        }
        return chain.proceed(builder.build())
    }
}
