package com.example.frontend

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface UserService {
    @GET("/v1/signIn")
    fun getSignIn(
        @Query("email") email: String,
        @Query("password") password: String,
    ): Call<User>
}