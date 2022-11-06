package net.gostartups.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import net.gostartups.myapplication.databinding.RecyclerFragmentBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RecyclerFragment : Fragment() {
    var db: CountryDatabase? = null

    lateinit var binding: RecyclerFragmentBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RecyclerFragmentBinding.inflate(inflater, container, false)

        db = activity?.baseContext?.let {
            Room.databaseBuilder(
                it, CountryDatabase::class.java, "country_db"
            ).build()
        }

        val countryDao = db?.countryDao()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountriesRepository(countryService)
        countryRepository.getCountries()?.enqueue(object : Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                val countries = response.body() ?: return
                for (i in 0..countries.size) {
                    val countryTest = countries[i]
//                    for (countryTest: Country in countries) {
                    Log.i("country", countryTest.toString())
                    val countryRoom = CountryRoom(
                        countryTest.alpha2Code,
                        countryTest.name,
                        countryTest.capital,
                        countryTest.region,
                        countryTest.population,
                        countryTest.area
                    )
                    Log.i("CountryRoom", countryRoom.toString())
                    GlobalScope.async {
                        countryDao?.insert(countryRoom)
                    }
                }


//                val mappedCountryList = countries.iterator().forEach {
//                    val countryRoom = Country(
//                        counter++,
//                        it.name,
//                        it.capital,
//                        it.region,
//                        it.population,
//                        it.area
//                    )
//                    GlobalScope.async {
//                        countryDao?.insert(countryRoom)
//                    }
//                    countryDao.insert(countryRoom)
//                }


                //                Log.i("mappedCountryList", mappedCountryList.toString())
                val adapter = CountryAdapter(countries)
                binding.countriesList.adapter = adapter
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Snackbar.make(binding.root, "Failed to fetch countries", Snackbar.LENGTH_LONG)
                    .show()
            }
        })
        return binding.root
    }

}