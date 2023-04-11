package com.example.frontend

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

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
                    ).build()
                }
            return instance
        }

        fun destroyInstance(){
            instance = null
        }
    }
}