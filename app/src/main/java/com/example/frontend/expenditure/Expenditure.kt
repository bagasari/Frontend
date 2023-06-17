package com.example.frontend.expenditure
import com.google.gson.annotations.SerializedName

data class GetExpenditureDTO(
    @SerializedName("name") val name: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("totalPrice") val totalPrice: Int,
    @SerializedName("productsByDate") val productsByDate: List<ProductsByDate>
)

data class ProductsByDate(
    @SerializedName("purchaseDate") val purchaseDate: String,
    @SerializedName("products") val products: List<Product>
)

class Product(
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("city") val city: String,
    @SerializedName("productType") val productType: String
)

data class ExpenditureFoodDTO(
    @SerializedName("product") val postProduct: PostProduct,
    @SerializedName("food") val food: Food
)

data class PostProduct(
    @SerializedName("accountBookId") val accountBookId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("price") val price: Int,
    @SerializedName("purchaseDate") val purchaseDate: String,
    @SerializedName("detail") val detail: String,
    @SerializedName("country") val country: String,
    @SerializedName("city") val city: String
)

data class Food(
    @SerializedName("count") val count: Int,
    @SerializedName("weight") val weight: Int,
    @SerializedName("latitude") val latitude: String,
    @SerializedName("longitude") val longitude: String
)

