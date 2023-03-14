package com.example.myproductsapp_kotlin.productslist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.myproductsapp_kotlin.R
import com.example.myproductsapp_kotlin.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductListBinding
    lateinit var fragment: ProductListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        if (savedInstanceState == null) {
            fragment = ProductListFragment()
            fragmentTransaction.add(R.id.listFragmentContainerView, fragment, "listFragment")
        }
        fragmentTransaction.commit()
    }

}