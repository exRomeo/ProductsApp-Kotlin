package com.example.myproductsapp_kotlin

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproductsapp_kotlin.databinding.FragmentProductListBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

const val TAG = "TAG"

class ProductListFragment : Fragment(),OnProductClick {

    private lateinit var binding: FragmentProductListBinding
    lateinit var adapter: ProductsAdapter

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

        if (checkConnection()) {
            lifecycleScope.launch(Dispatchers.IO) {
                val response = RetrofitClient.api.getAllProducts()
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful)
                        response.body()?.products?.let {
                            setList(it)
                            saveData(it)
                            Toast.makeText(
                                this@ProductListFragment.requireContext(),
                                "Showing Latest data",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    else
                        Toast.makeText(
                            this@ProductListFragment.requireContext(),
                            "Couldn't fetch data",
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        } else {
            lifecycleScope.launch(Dispatchers.IO) {
                val list = getData()
                withContext(Dispatchers.Main) {
                    setList(list)
                    Toast.makeText(
                        this@ProductListFragment.requireContext(),
                        "Showing offline data",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }


    private fun setList(list: List<Product>) {
        adapter.submitList(list)
    }

    private fun iniRecyclerView() {
        adapter = ProductsAdapter(this) /*{ product: Product ->
            Log.i(TAG, "lambda: start")
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
                Log.i(TAG, "lambda: portrait")
                startActivity(
                    Intent(
                        this.context, SingleProductActivity::class.java
                    ).putExtra("product", product)
                )
            } else {
                Log.i(TAG, "lambda: landscape")
                (this.activity as ProductListActivity).product = product
            }
        }*/ /*{ viewProduct }*/
        binding.productsList.layoutManager = LinearLayoutManager(this.requireContext())
        binding.adapter = adapter

    }

    private fun checkConnection(): Boolean {
        val connManager = this.requireContext()
            .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork =
            connManager.getNetworkCapabilities(connManager.activeNetwork) ?: return false
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            else -> false
        }
    }

    private suspend fun saveData(list: List<Product>) {
        val dao = RoomClient.getInstance(this.requireContext()).getProductDao()
        var i = 0
        for (p in list) {
            i++
            if (i % 2 == 0 && i < 10)
                dao.addProduct(p)
        }
    }

    private suspend fun getData(): List<Product> {
        val dao = RoomClient.getInstance(this.requireContext()).getProductDao()
        return dao.getOfflineProducts()
    }


    val viewProduct = { product: Product ->
        Log.i(TAG, "lambda: start")
        if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.i(TAG, "lambda: portrait")
            startActivity(
                Intent(
                    this.context, SingleProductActivity::class.java
                ).putExtra("product", product)
            )
        } else {
            Log.i(TAG, "lambda: landscape")
            (this.activity as ProductListActivity).product = product
        }
    }

    override fun onClick(product: Product) {
       viewProduct(product)
    }
}