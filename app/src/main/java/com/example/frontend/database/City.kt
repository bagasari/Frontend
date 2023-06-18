package com.example.frontend.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity( tableName = "city_table",
    foreignKeys = [
        ForeignKey(
            entity = Country::class,
            parentColumns = ["country_id"],
            childColumns = ["country_id"]
        )
    ])
data class City(
    @PrimaryKey(autoGenerate = true) val city_id : Long?,
    @ColumnInfo(name = "name") val name : String?,
    @ColumnInfo(name = "name_eng") val name_eng : String?,
    @ColumnInfo(name = "img") val img : Int?,
    @ColumnInfo(name = "country_id") val country_id : Long?
)

data class CityNames(
    val name: String,
    val name_eng: String
)

