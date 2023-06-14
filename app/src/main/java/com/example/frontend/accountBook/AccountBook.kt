package com.example.frontend.accountBook

import com.example.frontend.dto.Destination
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

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
    @SerializedName("city") val cityList: ArrayList<Destination>
)