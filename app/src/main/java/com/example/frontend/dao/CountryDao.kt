package com.example.frontend.dao

import androidx.room.*
import com.example.frontend.Country

@Dao
interface CountryDao {

    // 삽입문
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCountry(country: Country)

    // 삭제문
    @Delete
    suspend fun deleteCountry(country: Country)

    // 조회문
    // 국가 전체 조회
    @Query("SELECT * FROM country_table")
    suspend fun getAllCountries() : List<Country>

    // 인자로 받은 국가명에 해당하는 국가 행을 리턴
    @Query("SELECT * FROM country_table WHERE country_name LIKE :country_name")
    suspend fun getCountryByName(country_name: String): Country

    // 인자로 받은 국가명에 해당하는 국가 id 리턴
    @Query("SELECT country_id FROM country_table WHERE country_name LIKE :country_name")
    suspend fun getCountryId(country_name: String): Long

    // 국가 전체 삭제 쿼리
    @Query("DELETE FROM country_table")
    suspend fun deleteAll()

    // 국가 이미지 정보 변환 쿼리
    @Query("UPDATE country_table SET country_img = :country_img")
    suspend fun updateCountryImg(country_img: Int)

}