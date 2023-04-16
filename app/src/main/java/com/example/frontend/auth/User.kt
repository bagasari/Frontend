package com.example.frontend.auth

import com.google.gson.annotations.SerializedName

data class SignInRequest(
    @SerializedName("email") val email:String,
    @SerializedName("password") val password:String
)
data class SignInResponse(
    @SerializedName("accessToken") val accessToken:String,
    @SerializedName("refreshToken") val refreshToken:String
)

data class SignUpRequest(
    @SerializedName("email") val email:String,
    @SerializedName("password") val password: String,
    @SerializedName("name") val name: String
)