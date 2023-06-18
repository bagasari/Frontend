package com.example.frontend.database

import androidx.room.*
import com.example.frontend.database.City
import com.example.frontend.dto.Destination

@Dao
interface CityDao {

    // 삽입문
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCity(city: City)

    // 삭제문
    @Delete
    suspend fun deleteCity(city: City)

    // 조회문
    @Query("SELECT * FROM city_table")
    suspend fun getAllCities(): List<City>

    // 인자로 받은 국가 id를 갖는 도시 리스트 조회
    @Query("SELECT * FROM city_table WHERE country_id LIKE :country_id")
    suspend fun getCityOfCountry(country_id : Long): List<City>

    // 인자로 받은 도시명에 해당하는 도시 행 조회
    @Query("SELECT * FROM city_table WHERE name LIKE :city_name")
    suspend fun getCityByName(city_name : String): City

    // 도시 전체 삭제 쿼리
    @Query("DELETE FROM city_table")
    suspend fun deleteAll()

    // 도시명과 이미지 리스트 조회
    @Query("SELECT name, img FROM city_table")
    suspend fun getCityNameAndImg(): List<Destination>

    @Query("SELECT name, name_eng FROM city_table")
    suspend fun getCityNameEngAndKOR(): List<CityNames>

}