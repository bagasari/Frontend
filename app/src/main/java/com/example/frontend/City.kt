package com.example.frontend

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
    @ColumnInfo(name = "city_name") val city_name : String?,
    @ColumnInfo(name = "city_name_eng") val city_name_eng : String?,
    @ColumnInfo(name = "city_img") val city_img : Int?,
    @ColumnInfo(name = "country_id") val country_id : Long?
)


