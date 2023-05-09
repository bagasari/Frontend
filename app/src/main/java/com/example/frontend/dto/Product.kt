package com.example.frontend.dto

data class Product(
    val name : String,
    val price : Int,
    val destinationList : ArrayList<Destination>,
    val productType : String,
    val purchaseDate : String
){}
