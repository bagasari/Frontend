package com.example.frontend.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("/v1/auth/signIn")
    suspend fun postSignIn(
        @Body request: User
    ): Response<String>

    @POST("/v1/auth/signUp")
    suspend fun postSignUp(
        @Body request: User
    ): Response<String>
}

