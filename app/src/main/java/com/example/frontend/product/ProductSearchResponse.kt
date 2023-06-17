package com.example.frontend.product

import com.google.gson.annotations.SerializedName

data class ProductSearchResponse(
    @SerializedName("name")
    val name: List<String>
)