package net.gostartups.myapplication

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CountryRoom(
    @PrimaryKey val alpha2Code: String,
    @ColumnInfo(name = "country_name") val name: String,
    @ColumnInfo(name = "capital_name") val capital: String,
    @ColumnInfo(name = "region_name") val region: String,
    @ColumnInfo(name="country_population") val population: Int,
    @ColumnInfo(name="country_area") val area: Float
    )
