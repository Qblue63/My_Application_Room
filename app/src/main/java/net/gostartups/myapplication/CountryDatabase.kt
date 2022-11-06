package net.gostartups.myapplication

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CountryRoom::class], version = 1)
abstract class CountryDatabase : RoomDatabase() {
    abstract fun countryDao(): CountryDao
}