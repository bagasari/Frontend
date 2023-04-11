package com.example.frontend

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country_table")
data class Country(
    @PrimaryKey(autoGenerate = true) val country_id : Long?,
    @ColumnInfo(name = "country_name") val country_name : String?,
    @ColumnInfo(name = "country_img") val country_img : Int?
)
