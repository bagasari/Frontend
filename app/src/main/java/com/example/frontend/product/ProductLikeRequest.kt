package com.example.frontend.product

import com.google.gson.annotations.SerializedName

data class ProductLikeRequest(
    @SerializedName("authInfo")
    val authInfo: AuthInfo,
    @SerializedName("dto")
    val dto: DTO
) {
    data class AuthInfo(
        @SerializedName("email")
        val email: String
    )

    data class DTO(
        @SerializedName("productId")
        val productId: Long
    )
}
