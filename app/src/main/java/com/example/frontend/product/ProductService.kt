package com.example.frontend.product

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {
    @GET("/v1/product/search")
    suspend fun getProductList(
        @Query("keyword") keyword: String?,
        @Query("location") location: String,
        @Query("sort") sort: String,
        @Query("lastId") lastId: Long?
    ): Response<ProductListResponse>

    @GET("/v1/product/search/auto")
    suspend fun getProductSearchAuto(
        @Query("word") word: String
    ): Response<ProductSearchResponse>

    @POST("/v1/product/map/food/static")
    suspend fun getProductMarkersStatic(
        @Body request: ProductMarkerStaticRequest
    ): Response<List<List<ProductMarkerResponse>>>

    @POST("/v1/product/map/food/dynamic")
    suspend fun getProductMarkersDynamic(
        @Body request: ProductMarkerDynamicRequest
    ): Response<List<List<ProductMarkerResponse>>>

    @POST("/v1/product/like")
    suspend fun postProductLike(
        @Body request: ProductLikeRequest
    ): Response<String>

    @POST("/v1/product/dislike")
    suspend fun postProductDisLike(
        @Body request: ProductLikeRequest
    ): Response<String>
}