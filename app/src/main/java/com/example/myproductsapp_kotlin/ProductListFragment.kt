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
import com.example.myproductsapp_kotlin.databinding.FragmentProductListBinding
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG = "TAG"

class ProductListFragment : Fragment(), OnProductClick {

    private lateinit var binding: FragmentProductListBinding
    private lateinit var adapter: ProductsAdapter
    private val repository by lazy { Repository(this.requireContext()) }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniRecyclerView()

        if (repository.checkConnection()) {
            showOnlineData()
        } else {
            showOfflineData()
        }
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

    private fun showOnlineData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val response = repository.getAllProducts()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) response.body()?.products?.let {
                    adapter.submitList(it)
                    Toast.makeText(
                        this@ProductListFragment.requireContext(),
                        "Showing Latest data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else Toast.makeText(
                    this@ProductListFragment.requireContext(),
                    "Couldn't fetch data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun showOfflineData() {
        lifecycleScope.launch(Dispatchers.IO) {
            val list = repository.getOfflineData()
            withContext(Dispatchers.Main) {
                adapter.submitList(list)
                Toast.makeText(
                    this@ProductListFragment.requireContext(),
                    "Showing offline data",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}