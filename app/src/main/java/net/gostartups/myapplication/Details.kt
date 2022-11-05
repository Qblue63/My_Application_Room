package net.gostartups.myapplication

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import net.gostartups.myapplication.databinding.CountryDetailsBinding
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Details(private val countryName: String) : Fragment() {

    lateinit var binding: CountryDetailsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = CountryDetailsBinding.inflate(inflater, container, false)

        val retrofit = Retrofit.Builder().baseUrl("https://restcountries.com/v2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(OkHttpClient())
            .build()

        val countryService = retrofit.create(CountryService::class.java)
        val countryRepository = CountriesRepository(countryService)
        countryRepository.getDetails(countryName)
            ?.enqueue(object : Callback<List<CountryDetails>> {
                override fun onResponse(
                    call: Call<List<CountryDetails>>, response: Response<List<CountryDetails>>
                ) {
                    val details = response.body()?.get(0) ?: return
                    Log.d("details", details.toString())
                    binding.apply {
                        country = details.name
                        capital = details.capital
                        region = details.region
                        population = details.population.toString()
                        area = details.area.toString()

                        Glide
                            .with(this.root.context)
                            .load(details.flags.png)
                            .fitCenter()
                            .placeholder(R.drawable.ic_launcher_foreground)
                            .into(ivFlag)
                    }
                    binding.backButton.setOnClickListener() {
                        requireActivity().supportFragmentManager.popBackStack()
                    }
                }

                override fun onFailure(call: Call<List<CountryDetails>>, t: Throwable) {
                    Snackbar.make(binding.root, "Failed to fetch countries", Snackbar.LENGTH_LONG)
                        .show()
                }
            })
        return binding.root
    }
}