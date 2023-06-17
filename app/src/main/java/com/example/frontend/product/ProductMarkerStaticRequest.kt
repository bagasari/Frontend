package com.example.frontend.product

import com.google.gson.annotations.SerializedName

data class ProductMarkerStaticRequest(
    @SerializedName("name")
    val name: String?,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)