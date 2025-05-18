package com.useapi.foodallergydetector.data.model

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token")    // ← ensure the key matches exactly
    val jwt: String?           // allow null so parsing won’t throw
)