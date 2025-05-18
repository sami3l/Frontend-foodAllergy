package com.useapi.foodallergydetector.data.network

import com.useapi.foodallergydetector.data.model.ScanRequest
import com.useapi.foodallergydetector.data.model.ScanResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ScanApi {
    @POST("/api/evaluate")
    suspend fun evaluate(@Body request: ScanRequest): ScanResponse

    @GET("/api/users/scans/user/{userId}")
    suspend fun getHistory(
        @Path("userId") userId: String,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20
    ): List<ScanResponse>
}
