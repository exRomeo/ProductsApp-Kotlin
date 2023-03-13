package com.example.myproductsapp_kotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.example.myproductsapp_kotlin.databinding.FragmentSingleProductBinding

class SingleProductFragment : Fragment() {

    lateinit var binding: FragmentSingleProductBinding
    lateinit var product: Product

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        product = (arguments?.getSerializable("product") ?: Product()) as Product

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_single_product,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI(product)
        binding.addButton.setOnClickListener {
            Toast.makeText(
                this.context,
                "${product.title}\nadded to favorites",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    fun updateUI(product: Product) {
        binding.product = product
    }
}