package com.example.frontend.dto

import java.util.*
import kotlin.collections.ArrayList

class AccountBook(
    val id: Long,
    val name: String,
    val startDate: Date,
    val endDate: Date,
    val img: Int,
    val isPrivate: Boolean,
    val totalPrice: Int,
    // val destinationList: ArrayList<Destination>
) {
}