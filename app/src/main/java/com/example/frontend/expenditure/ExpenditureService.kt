package com.example.frontend.expenditure

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ExpenditureService {

    @GET("/v1/account")
    suspend fun getExpenditureByAccountBookId(
        @Query("accountId") accountId: Long
    ): Response <GetExpenditureDTO>

    @POST("/v1/account/product/food")
    suspend fun createFoodExpenditure(
        @Body request: ExpenditureFoodDTO
    ): Response <String>
}