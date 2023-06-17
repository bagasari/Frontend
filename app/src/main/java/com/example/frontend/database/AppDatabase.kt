package com.example.frontend.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(entities = [Country::class, City::class], version=5)
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
                        "travelList.db"
                    )
                        .addCallback(object: RoomDatabase.Callback(){
                            override fun onCreate(db: SupportSQLiteDatabase) {
                                super.onCreate(db)
                                // 국가 정보 삽입
                                db.execSQL("insert into country_table (country_id, name, name_eng, img) values (1, '일본', 'japan', 1)")
                                db.execSQL("insert into country_table (country_id, name, name_eng, img) values (2, '중국', 'china', 2)")
                                db.execSQL("insert into country_table (country_id, name, name_eng, img) values (3, '필리핀', 'philippine', 3)")
                                db.execSQL("insert into country_table (country_id, name, name_eng, img) values (4, '미국', 'usa', 4)")

                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (1, '뉴올리언스', 'neworleans', 5, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (2, '뉴욕', 'newyork', 6, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (3, '라스베가스', 'lasvegas', 6, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (4, '보스턴', 'boston', 6, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (5, '샌프란시스코', 'sanfrancisco', 6, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (6, '텍사스', 'texas', 6, 4)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (7, '하와이', 'hawaii', 6, 4)")

                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (8, '교토', 'kyoto', 6, 1)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (9, '도쿄', 'tokyo', 6, 1)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (10, '삿포로', 'sapporo', 6, 1)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (11, '오사카', 'osaka', 6, 1)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (12, '후쿠오카', 'fukuoka', 6, 1)")

                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (13, '광저우', 'guangzhou', 6, 2)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (14, '베이징', 'beijing', 6, 2)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (15, '상하이', 'shanghai', 6, 2)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (16, '항저우', 'hangzhou', 6, 2)")

                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (17, '마닐라', 'manila', 6, 3)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (18, '보라카이', 'boracay', 6, 3)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (19, '보홀', 'bohol', 6, 3)")
                                db.execSQL("insert into city_table (city_id, name, name_eng, img, country_id) values (20, '세부', 'cebu', 6, 3)")

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