package net.gostartups.myapplication

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface CountryDao {
    @Query("SELECT * FROM countryroom")
    suspend fun getAllCountries(): List<CountryRoom>

    @Query("SELECT * FROM countryroom where alpha2Code=:id")
    suspend fun getCountryById(id: String): CountryRoom

    @Insert
    suspend fun insertAll(vararg countryRoom: CountryRoom)

    @Insert
    suspend fun insert(countryRoom: CountryRoom)

    @Delete
    suspend fun delete(countryRoom: CountryRoom)

    @Update
    suspend fun update(vararg countryRoom: CountryRoom)
}