package com.example.frontend.product

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ProductService {
    @GET("/v1/product/search")
    suspend fun getProductList(
        @Query("keyword") keyword: String?,
        @Query("location") location: String,
        @Query("sort") sort: String,
        @Query("lastId") lastId: Int?
    ): Response<ProductListResponse>

    @GET("/v1/product/search/auto")
    suspend fun getProductSearchAuto(
        @Query("word") word: String
    ): Response<ProductSearchResponse>
}