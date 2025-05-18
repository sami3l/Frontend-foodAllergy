package com.useapi.foodallergydetector.data.network

import com.useapi.foodallergydetector.data.store.TokenPreferences
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

class JwtInterceptor(
    private val tokenProvider: TokenPreferences
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
