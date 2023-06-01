package com.example.frontend.product

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("/v1/product/search")
    suspend fun getProductSearch(
        @Query("keyword") keyword: String,
        @Query("sort") sort: String,
        @Query("size") size: Int,
        @Query("lastId") lastId: Int
    ): Response<Product>

    @GET("/v1/product/search/auto")
    suspend fun getProductSearchAuto(
        @Query("word") word: String
    ): Response<ProductSearch>
}