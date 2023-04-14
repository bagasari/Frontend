package com.example.frontend.auth

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("accessToken") val accessToken:String,
    @SerializedName("refreshToken") val refreshToken:String,
)
