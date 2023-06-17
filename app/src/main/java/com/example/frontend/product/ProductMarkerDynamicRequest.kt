package com.example.frontend.product

import com.google.gson.annotations.SerializedName

data class ProductMarkerDynamicRequest(
    @SerializedName("id")
    val id: Long,
    @SerializedName("name")
    val name: String?,
)