package com.example.frontend.product

import com.google.gson.annotations.SerializedName

data class ProductLikeRequest (
    @SerializedName("productId")
    val productId: Long
    )