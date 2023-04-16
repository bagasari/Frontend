package com.example.frontend.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/v1/auth/signIn")
    suspend fun postSignIn(
        @Body request: SignInRequest
    ): Response<SignInResponse>

    @POST("/v1/auth/signUp")
    suspend fun postSignUp(
        @Body request: SignUpRequest
    ): Response<String>
}