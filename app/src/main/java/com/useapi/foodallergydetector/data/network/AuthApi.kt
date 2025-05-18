package com.useapi.foodallergydetector.data.network

import com.useapi.foodallergydetector.data.model.AuthRequest
import com.useapi.foodallergydetector.data.model.AuthResponse
import com.useapi.foodallergydetector.data.model.ScanRequest
import com.useapi.foodallergydetector.data.model.ScanResponse
import retrofit2.http.*

interface AuthApi {
    @POST("/api/auth/login")
    suspend fun login(@Body request: AuthRequest): AuthResponse

    @POST("/api/evaluate")
    suspend fun evaluate(@Body req: ScanRequest): ScanResponse

    @GET("/api/users/scans/user/{userId}")
    suspend fun getHistory(@Path("userId") userId: String): List<ScanResponse>
}