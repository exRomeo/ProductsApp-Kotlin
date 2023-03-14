package com.example.myproductsapp_kotlin.singleproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.myproductsapp_kotlin.R
import com.example.myproductsapp_kotlin.databinding.FragmentSingleProductBinding
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SingleProductFragment : Fragment() {

    lateinit var binding: FragmentSingleProductBinding
    lateinit var product: Product
    private val repository by lazy { Repository(this.requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        product = (arguments?.getSerializable("product") ?: Product()) as Product

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_single_product, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        updateUI(product)
        binding.addButton.setOnClickListener {
            action(product)
            Toast.makeText(
                this.context, "${product.title}\nadded to favorites", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateUI(product: Product) {
        binding.product = product
        binding.addButton.setImageDrawable(
            if (product.isFavorite)
                ContextCompat.getDrawable(this.requireContext(),R.drawable.trash)
            else
                ContextCompat.getDrawable(this.requireContext(), R.drawable.heart)
        )
    }

    private fun action(product: Product) {
        lifecycleScope.launch(Dispatchers.IO) {
            if (product.isFavorite)
                repository.removeFromFavorites(product)
            else
                repository.addToFavorites(product)
            withContext(Dispatchers.Main) { updateUI(product) }
        }

    }
}