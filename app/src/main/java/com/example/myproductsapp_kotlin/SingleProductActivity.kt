package com.example.myproductsapp_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myproductsapp_kotlin.databinding.ActivitySingleProductBinding

class SingleProductActivity : AppCompatActivity() {
    lateinit var binding: ActivitySingleProductBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySingleProductBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        if (savedInstanceState == null) {
            val fragment = SingleProductFragment()
            val bundle = Bundle()
            bundle.putSerializable(
                "product",
                intent.getSerializableExtra("product") as Product

            )
            fragment.arguments = bundle
            fragmentTransaction.add(R.id.fragmentContainerView, fragment, "single")
        }
        fragmentTransaction.commit()
    }
}