package com.example.frontend.dto

data class Food(
    var accountBookId : Long,
    var name : String,
    var price : Int,
    var purchaseDate : String,
    var detail : String,
    var country : String,
    var city : String,
    var count : Int,
    var weight : Int,
    var latitude : String,
    var Longitude : String
) {
}