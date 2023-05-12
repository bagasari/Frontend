package com.example.frontend.product

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("/search/product")
    suspend fun getProductSearchResults(
        @Query("keyword") keyword: String
    ): Response<ArrayList<TestProduct>>
}