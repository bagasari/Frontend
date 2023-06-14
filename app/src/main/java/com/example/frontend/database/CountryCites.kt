package com.example.frontend.database

import androidx.room.Embedded
import androidx.room.Relation

data class CountryCites(
    @Embedded val country: Country,
    @Relation(
        parentColumn = "country_id",
        entityColumn = "country_id"
    )
    val cities: List<City>?
)


