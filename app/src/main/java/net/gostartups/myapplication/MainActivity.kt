package net.gostartups.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.gostartups.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(
                R.id.fragment_container_view, RecyclerFragment()
            )
            transaction.addToBackStack("first_transaction")
            transaction.commit()
        }
    }
}