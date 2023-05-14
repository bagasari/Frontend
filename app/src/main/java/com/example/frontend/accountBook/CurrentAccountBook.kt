package com.example.frontend.accountBook

import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

data class CurrentAccountBook(
    @SerializedName("name") val name: String,
    @SerializedName("startDate") val startDate: Date,
    @SerializedName("endDate") val endDate: Date,
    @SerializedName("totalPrice") val totalPrice: Int,
    @SerializedName("productsByDate") val productsByDate: ArrayList<ProductsByDate>

)
