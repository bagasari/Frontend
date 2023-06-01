package com.example.frontend.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/** Retrofit 객체를 싱글톤 으로 생성 -> Retrofit 객체 생성 최소화, 앱 전체에 공유 하여 사용 가능**/
object RetrofitClient {
    // API 의 기본 URL
    private const val BASE_URL = "http://43.201.221.222:8080"

    // 타임 아웃 시간 (10초)
    private const val CONNECT_TIMEOUT_SEC = 10000L

    // gson 객체 - JSON 데이터 를 파싱 하도록 함
    private val gson = GsonBuilder().setLenient().create()

    // interceptor 기본 설정 - HTTP 요청과 응답을 intercept 하여 로그를 출력 하도록 설정
    private val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    // OkHttpClient 기본 설정 - interceptor 및 시간 초과 설정
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS)
        .build()

    // Retrofit 초기 설정
    private val retrofitBuilder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(gson))

    // Retrofit 객체
    private var retrofit: Retrofit = retrofitBuilder.build()

    // Retrofit 객체 반환
    fun getInstance(): Retrofit = retrofit

    // Retrofit 객체에 accessToken 추가
    fun setAccessToken(accessToken: String) {
        // okHttpClient 에 authInterceptor 추가
        val authInterceptor = AuthInterceptor(accessToken = accessToken)
        val newOkHttpClient = okHttpClient.newBuilder()
            .addInterceptor(authInterceptor)
            .build()

        // 수정된 okHttpClient Retrofit 에 적용
        retrofit = retrofitBuilder
            .client(newOkHttpClient)
            .build()
    }
}