package com.example.frontend.auth

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("email") val email:String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String = "",
    @SerializedName("token") val token: String = ""
)