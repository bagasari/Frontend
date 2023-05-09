package com.example.frontend.dto

data class Transportation(
    var accountBookId : Long,
    var name : String,
    var price : Int,
    var purchaseDate : String,
    var detail : String,
    var country : String,
    var city : String,
    var startLatitude : String,
    var startLongitude : String,
    var endLatitude : String,
    var endLongitude : String,
    var transportType : String
) {
}