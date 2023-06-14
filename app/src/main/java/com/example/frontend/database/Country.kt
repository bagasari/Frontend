package com.example.frontend.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_table")
data class Country(
    @PrimaryKey(autoGenerate = true) val country_id : Long?,
    @ColumnInfo(name = "name") val name : String?,
    @ColumnInfo(name = "name_eng") val name_eng : String?,
    @ColumnInfo(name = "img") val img : Int?
)
