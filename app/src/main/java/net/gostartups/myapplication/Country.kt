package net.gostartups.myapplication

/**
 * Created by borisnikolov on 24 Oct 2022.
 */
data class Country(
    var alpha2Code:String,
    var name: String,
    var capital: String,
    var region: String,
    var population: Int,
    var area: Float,
    var flags: Flags
)

data class Flags(
    var svg: String,
    var png: String
)

data class CountryDetails(
    var name: String,
    var capital: String,
    var region: String,
    var subregion: String,
    var population: Int,
    var area: Int,
    var flags: Flags
)