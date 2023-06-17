package com.example.frontend.product

import com.google.gson.annotations.SerializedName

data class ProductMarkerResponse(
    @SerializedName("product") val product: Product,
    @SerializedName("food") val food: Food
) {
    data class Product(
        @SerializedName("id") val id: Int,
        @SerializedName("accountBookId") val accountBookId: Int,
        @SerializedName("name") val name: String,
        @SerializedName("price") val price: Int,
        @SerializedName("purchaseDate") val purchaseDate: String,
        @SerializedName("detail") val detail: String,
        @SerializedName("country") val country: String,
        @SerializedName("city") val city: String,
        @SerializedName("like") val like: Int
    )

    data class Food(
        @SerializedName("count") val count: Int,
        @SerializedName("weight") val weight: Int,
        @SerializedName("latitude") val latitude: Double,
        @SerializedName("longitude") val longitude: Double
    )
}