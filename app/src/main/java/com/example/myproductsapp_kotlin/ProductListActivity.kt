package com.example.myproductsapp_kotlin

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myproductsapp_kotlin.databinding.ActivityProductListBinding

class ProductListActivity : AppCompatActivity() {
    lateinit var binding: ActivityProductListBinding
    lateinit var fragment: ProductListFragment
    lateinit var fragment2: SingleProductFragment
    var product: Product = Product()
        set(value) {
            field = value
            fragment2.updateUI(product)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            if (savedInstanceState == null) {
                fragment = ProductListFragment()
                fragmentTransaction.add(R.id.listFragmentContainerView, fragment, "listFragment")
            }
            fragmentTransaction.commit()
        } else {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()
            fragment2 = SingleProductFragment()
            fragmentTransaction.replace(R.id.fragmentContainerView3, fragment2, "singleFragment")
            fragmentTransaction.commit()
        }
    }

}