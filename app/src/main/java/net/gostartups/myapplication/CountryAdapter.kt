package net.gostartups.myapplication

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import net.gostartups.myapplication.databinding.CountryItemBinding

class CountryAdapter(private val countries: List<Country>) :
    RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    class CountryViewHolder(val binding: CountryItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = CountryItemBinding.inflate(layoutInflater, parent, false)

        return CountryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentCountry = countries[position]
        holder.binding.apply {
            country = currentCountry.name
            capital = currentCountry.capital
//            region = currentCountry.region
//            population = currentCountry.population.toString()
//            area = currentCountry.area.toString()

            Glide
                .with(this.root.context)
                .load(currentCountry.flags.png)
                .fitCenter()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(ivFlag)
        }

        holder.binding.root.setOnClickListener {
            Snackbar.make(it, currentCountry.name, Snackbar.LENGTH_LONG).show()
            val activity = it.context as AppCompatActivity
            val transaction = activity.supportFragmentManager.beginTransaction()
            transaction.replace(
                R.id.fragment_container_view, Details(currentCountry.name)
            )
            transaction.addToBackStack("first_transaction")
            transaction.commit()
        }
    }

    override fun getItemCount() = countries.size
}