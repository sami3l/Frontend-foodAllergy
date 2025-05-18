package com.useapi.foodallergydetector.data.model

import com.google.gson.annotations.SerializedName

data class ScanResponse(
    /** List of detected allergen names (e.g. ["milk","peanut"]) */
    @SerializedName("detectedAllergens")
    val detectedAllergens: List<String>,

    /** Risk level determined by the backend: "None", "Low", "Moderate", "High" */
    @SerializedName("riskLevel")
    val riskLevel: String,

    /** The name of the product scanned (if provided) */
    @SerializedName("productName")
    val productName: String,

    /** URL to the product image (if the API found one), or null */
    @SerializedName("imageUrl")
    val imageUrl: String?,

    /** Source of the scan: "API" (barcode) or "OCR" (manual text) */
    @SerializedName("source")
    val source: String,

    /** ISO-8601 timestamp when the scan was created, e.g. "2025-05-17T22:11:03" */
    @SerializedName("createdAt")
    val createdAt: String
)
