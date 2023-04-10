package com.example.frontend.retrofit

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

/** Retrofit 객체를 싱글톤으로 생성 -> Retrofit 객체 생성 최소화, 앱 전체에 공유하여 사용 가능**/
object RetrofitClient {
    // Retrofit 객체 - 없으면 생성, 있으면 반환
    private var retrofit: Retrofit? = null
    // gson 객체 - JSON 데이터를 파싱하도록 함
    private val gson = GsonBuilder().setLenient().create()
    // API 의 기본 URL
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    // 타임아웃 시간을 설정 (ms 기준)
    private const val CONNECT_TIMEOUT_SEC = 20000L

    /** Retrofit 객체가 없으면 생성, 있으면 Retrofit 객체 반환 **/
    fun getInstance() : Retrofit {
        if(retrofit == null) {
            // interceptor 설정
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            // OkHttpClient 설정
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor) // HTTP 요청과 응답을 intercept 하여 로그를 출력하도록 설정
                .connectTimeout(CONNECT_TIMEOUT_SEC, TimeUnit.SECONDS) // 시간초과 설정
                .build()

            // retrofit 설정
            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!
    }
}