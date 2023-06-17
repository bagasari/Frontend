package com.example.frontend.accountBook

import com.google.gson.annotations.SerializedName
import java.util.*

data class AccountBook(
    val id: Long,
    val name: String,
    val startDate: Date,
    val endDate: Date,
    val img: Int,
    val isPrivate: Boolean,
    val totalPrice: Int
)

data class GetAccountBookDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("city") val city: String
)

data class PostAccountBookDTO(
    @SerializedName("name") val name: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("isPrivate") val isPrivate: Boolean,
    @SerializedName("cityList") val cityList: List<String>
)

data class GetCurrentAccountBookDTO(
    @SerializedName("name") val name: String,
    @SerializedName("startDate") val startDate: String,
    @SerializedName("endDate") val endDate: String,
    @SerializedName("totalPrice") val totalPrice: Int,
    @SerializedName("productsByDate") val productsByDate: List<ProductsByDate>
)




