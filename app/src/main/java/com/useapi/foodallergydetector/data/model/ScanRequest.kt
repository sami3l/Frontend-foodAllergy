package com.useapi.foodallergydetector.data.model

import com.google.gson.annotations.SerializedName

data class ScanRequest(
    /** MongoDB userId of the logged-in user */
    @SerializedName("userId")
    val userId: String,

    /** EAN/UPC barcode, if you’re using the API lookup */
    @SerializedName("barcode")
    val barcode: String? = null,

    /** Free-text ingredients for OCR/manual scans */
    @SerializedName("productText")
    val productText: String? = null,

    /** Optional human-readable product name */
    @SerializedName("productName")
    val productName: String? = null
)
