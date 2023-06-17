package com.example.frontend.accountBook

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AccountService {

    // 현재 작성중인 가계부 - 홈 화면
    @GET("/v1/account/current")
    suspend fun getCurrentAccount(
    ): Response <GetCurrentAccountBookDTO> // : name, startDate, endDate, totalPrice, productsByDate(purchaseDate, products(name, price, city, category))

    // 가계부 리스트 - 홈 화면, 가계부 목록 화면
    @GET("/v1/account/list")
    suspend fun getAccountBookList(
    ): Response <List<GetAccountBookDTO>> // : AccountBook(id, name, startDate, endDate, city)

    // 가계부 생성하기
    @POST("/v1/account/create")
    suspend fun createAccountBook(
        @Body request: PostAccountBookDTO
    ): Response <String>

}