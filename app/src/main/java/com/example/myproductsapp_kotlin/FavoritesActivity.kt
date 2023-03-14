package com.example.myproductsapp_kotlin

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myproductsapp_kotlin.databinding.ActivityFavoritesBinding

class FavoritesActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoritesBinding
    lateinit var fragment: FavoritesFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (savedInstanceState == null) {
            fragment = FavoritesFragment()
            fragmentTransaction.add(R.id.favoritesContainer, fragment, "listFragment")
        }
        fragmentTransaction.commit()
    }
}