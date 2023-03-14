package com.example.myproductsapp_kotlin

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproductsapp_kotlin.databinding.FragmentFavoritesBinding
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FavoritesFragment : Fragment(),OnProductClick {
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: ProductsAdapter
    private val repository by lazy { Repository(this.requireContext()) }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorites, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniRecyclerView()
        showFavorites()
    }



    private fun iniRecyclerView() {
        adapter = ProductsAdapter(this)
        binding.productsList.layoutManager = LinearLayoutManager(this.requireContext())
        binding.adapter = adapter

    }

    val viewProduct = { product: Product ->
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            startActivity(
                Intent(
                    this.context, SingleProductActivity::class.java
                ).putExtra("product", product)
            )
        } else {
            (this.activity as ProductListActivity).product = product
        }
    }


    override fun onClick(product: Product) {
        viewProduct(product)
    }

    private fun showFavorites() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list = repository.getFavorites()
            withContext(Dispatchers.Main) {
                adapter.submitList(list)
                Toast.makeText(
                    this@FavoritesFragment.requireContext(),
                    "Showing Favorites",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}