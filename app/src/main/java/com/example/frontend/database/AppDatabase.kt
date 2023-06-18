package com.example.frontend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Country::class, City::class], version=6)
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
                        "location.db"
                    )
                        .addCallback(object: RoomDatabase.Callback(){
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                // 국가 정보 삽입
                                db.execSQL("insert into country_table (country_id, name, name_eng, img) values (1, '일본', 'Japan', 1)")
                                db.execSQL("insert into country_table (country_id, name, name_eng, img) values (2, '중국', 'China', 2)")
                                db.execSQL("insert into country_table (country_id, name, name_eng, img) values (3, '필리핀', 'Philippine', 3)")
                                db.execSQL("insert into country_table (country_id, name, name_eng, img) values (4, '미국', 'Usa', 4)")

                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (1, '뉴올리언스', 'Neworleans', 5, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (2, '뉴욕', 'Newyork', 6, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (3, '네바다', 'Nevada', 6, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (4, '보스턴', 'Boston', 6, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (5, '캘리포니아', 'California', 6, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (6, '텍사스', 'Texas', 6, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (7, '하와이', 'Hawaii', 6, 4)")

                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (8, '교토', 'Kyoto', 6, 1)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (9, '도쿄', 'Tokyo', 6, 1)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (10, '삿포로', 'Sapporo', 6, 1)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (11, '오사카', 'Osaka', 6, 1)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (12, '후쿠오카', 'Fukuoka', 6, 1)")

                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (13, '광저우', 'Guangzhou', 6, 2)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (14, '베이징', 'Beijing', 6, 2)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (15, '상하이', 'Shanghai', 6, 2)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (16, '항저우', 'Hangzhou', 6, 2)")

                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (17, '마닐라', 'Manila', 6, 3)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (18, '보라카이', 'Boracay', 6, 3)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (19, '보홀', 'Bohol', 6, 3)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (20, '세부', 'Cebu', 6, 3)")

                            }
                        })
                        .fallbackToDestructiveMigration()
                        .build()
                }
            return instance
        }

        fun destroyInstance(){
            instance = null
        }
    }
}