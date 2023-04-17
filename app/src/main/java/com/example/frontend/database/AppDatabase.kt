package com.example.frontend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.frontend.City
import com.example.frontend.Country
import com.example.frontend.dao.CityDao
import com.example.frontend.dao.CountryAndCitiesDao
import com.example.frontend.dao.CountryDao

@Database(entities = [Country::class, City::class], version=1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun countryDao(): CountryDao
    abstract fun cityDao(): CityDao
    abstract fun CountryAndCitiesDao(): CountryAndCitiesDao

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun getInstance(context: Context): AppDatabase? {
            if(instance == null)
                synchronized(AppDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        "countryAndCity.db"
                    )
                        .addCallback(object: RoomDatabase.Callback(){
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                // 국가 정보 삽입
                                db.execSQL("insert into country_table (country_id, country_name, country_name_eng, country_img) values (1, '일본', 'japan', 101)")
                                db.execSQL("insert into country_table (country_id, country_name, country_name_eng, country_img) values (2, '중국', 'china', 102)")
                                db.execSQL("insert into country_table (country_id, country_name, country_name_eng, country_img) values (3, '필리핀', 'philippine', 103)")
                                db.execSQL("insert into country_table (country_id, country_name, country_name_eng, country_img) values (4, '미국', 'usa', 104)")

//                db!!.countryDao().insertCountry(Country(5, "인도네시아", R.drawable.Indonesia))
//                db!!.countryDao().insertCountry(Country(6, "베트남", R.drawable.vietnam))
//                db!!.countryDao().insertCountry(Country(7, "프랑스", R.drawable.france))
//                db!!.countryDao().insertCountry(Country(8, "영국", R.drawable.uk))
//                db!!.countryDao().insertCountry(Country(9, "이탈리아", R.drawable.italy))


                                //도시 정보 삽입
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (1, '오사카', 'osaka', 201, 1)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (2, '도쿄', 'tokyo', 202, 1)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (3, '교토', 'kyoto', 203, 1)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (4, '후쿠오카', 'fukuoka', 204, 1)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (5, '삿포로', 'sapporo', 205, 1)")
//
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (6, '베이징', 'beijing', 206, 2)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (7, '상하이', 'shanghai', 207, 2)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (8, '광저우', 'guangzhou', 208, 2)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (9, '항저우', 'hangzhou', 209, 2)")
//
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (10, '세부', 'cebu', 210, 3)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (11, '보라카이', 'boracay', 211, 3)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (12, '마닐라', 'maynila', 212, 3)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (13, '보홀', 'bohol', 213, 3)")
//
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (14, '뉴욕', 'newyork', 214, 4)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (15, '샌프란시스코', 'sanfrancisco', 215, 4)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (16, '뉴올리언스', 'neworleans', 216, 4)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (17, '텍사스', 'texas', 217, 4)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (18, '보스턴', 'boston', 218, 4)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (19, '라스베가스', 'lasvegas', 219, 4)")
//                                db.execSQL("insert into city_table (city_id, city_name, city_name_eng, city_img, country_id) values (20, '하와이', 'hawaii', 220, 4)")

//                                //  db!!.cityDao().insertCity(City(1, "발리", R.drawable.bali, db!!.countryDao().getCountryId("인도네시아")))
                            }
                        })
                        .build()
                }
            return instance
        }

        fun destroyInstance(){
            instance = null
        }
    }
}