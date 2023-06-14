package com.example.frontend.database

import androidx.room.*
import com.example.frontend.database.Country
import com.example.frontend.dto.Destination

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
    @Query("SELECT * FROM country_table WHERE name LIKE :country_name")
    suspend fun getCountryByName(country_name: String): Country

    // 인자로 받은 국가명에 해당하는 국가 id 리턴
    @Query("SELECT country_id FROM country_table WHERE name LIKE :country_name")
    suspend fun getCountryId(country_name: String): Long

    // 국가 전체 삭제 쿼리
    @Query("DELETE FROM country_table")
    suspend fun deleteAll()

    // 국가 이미지 정보 변환 쿼리
    @Query("UPDATE country_table SET img = :country_img")
    suspend fun updateCountryImg(country_img: Int)

    // 국가명과 이미지 리스트 조회
    @Query("SELECT name, img FROM country_table")
    suspend fun getCountryNameAndImg(): List<Destination>

}