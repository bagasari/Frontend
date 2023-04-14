package com.example.frontend.auth

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("/v1/signIn")
    suspend fun getSignIn(
        @Query("email") email: String,
        @Query("password") password: String,
    ): Response<User>

}