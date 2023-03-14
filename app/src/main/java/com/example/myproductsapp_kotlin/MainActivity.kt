package com.example.myproductsapp_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myproductsapp_kotlin.databinding.ActivityMainBinding
import com.example.myproductsapp_kotlin.favorites.FavoritesActivity
import com.example.myproductsapp_kotlin.productslist.ProductListActivity

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.listButton.setOnClickListener {
            intent = Intent(this, ProductListActivity::class.java)
            startActivity(intent)
        }
        binding.favsButton.setOnClickListener {
            intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
        binding.exitButton.setOnClickListener { finish() }

    }
}