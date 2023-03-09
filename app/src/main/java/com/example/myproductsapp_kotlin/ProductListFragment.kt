package com.example.myproductsapp_kotlin

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproductsapp_kotlin.databinding.FragmentProductListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG = "TAG"

class ProductListFragment : Fragment() {

    private lateinit var binding: FragmentProductListBinding
    lateinit var adapter: ProductsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniRecyclerView()

        lifecycleScope.launch(Dispatchers.IO) {
            val response = RetrofitClient.api.getAllProducts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful)
                    response.body()?.products?.let { setList(it) }
                else
                    Toast.makeText(
                        this@ProductListFragment.requireContext(),
                        "Couldn't fetch data",
                        Toast.LENGTH_SHORT
                    ).show()
            }
        }
    }


    private fun setList(list: List<Product>) {
        adapter.submitList(list)
    }

    private fun iniRecyclerView() {
        adapter = if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            ProductsAdapter() { product: Product ->
                startActivity(
                    Intent(
                        this.context, SingleProductActivity::class.java
                    ).putExtra("product", product)
                )
            }
        } else {
            ProductsAdapter { product: Product ->
                (this.activity as ProductListActivity).product = product
            }
        }
        binding.productsList.layoutManager = LinearLayoutManager(this.requireContext())
        binding.productsList.adapter = adapter

    }

}