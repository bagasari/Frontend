package com.example.frontend.auth

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("/v1/signIn")
    suspend fun postSignIn(
        @Query("email") email: String,
        @Query("password") password: String,
    ): Response<User>

    @POST("/v1/signUp")
    suspend fun postSignUp(
        @Query("email") email: String,
        @Query("password") password: String,
        @Query("username") username: String,
        @Query("role") role: String,
    ): Response<Int>

}