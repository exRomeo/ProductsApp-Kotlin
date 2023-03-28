package com.example.myproductsapp_kotlin.productslist

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproductsapp_kotlin.OnProductClick
import com.example.myproductsapp_kotlin.ProductsAdapter
import com.example.myproductsapp_kotlin.R
import com.example.myproductsapp_kotlin.databinding.FragmentProductListBinding
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository
import com.example.myproductsapp_kotlin.sealedclass.APIState
import com.example.myproductsapp_kotlin.singleproduct.SingleProductActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class ProductListFragment : Fragment(), OnProductClick {

    private lateinit var binding: FragmentProductListBinding
    private lateinit var adapter: ProductsAdapter
    private val repository by lazy { Repository(this.requireContext()) }
    private val viewModel: ProductListViewModel by lazy {
        ViewModelProvider(
            this.requireActivity(), ProductListViewModelFactory(repository)
        )[(ProductListViewModel::class.java)]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_product_list, container, false)
        binding.lifecycleOwner = this.viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniRecyclerView()
        lifecycleScope.launch {
            viewModel.productsList.collect {
                showResponse(it)
            }
        }
    }

    private fun iniRecyclerView() {
        adapter = ProductsAdapter(
            this, R.raw.unsave,
            R.raw.save
        )
        binding.productsList.layoutManager = LinearLayoutManager(this.requireContext())
        binding.adapter = adapter
    }

    val viewProduct = { product: Product ->
        startActivity(
            Intent(
                this.context, SingleProductActivity::class.java
            ).putExtra("product", product)
        )
    }

    override fun onItemClick(product: Product) {
        viewProduct(product)
    }

    override fun onButtonClick(product: Product) {
        if (product.isFavorite)
            viewModel.removeFavorite(product)
        else
            viewModel.addFavorite(product)
    }


    private fun showResponse(apiState: APIState) {
        when (apiState) {
            is APIState.Loading -> {
                binding.productsList.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
            is APIState.Success -> {
                binding.progressBar.visibility = View.GONE
                binding.productsList.visibility = View.VISIBLE
                binding.adapter!!.submitList(apiState.data)
            }
            is APIState.Failure -> {
                binding.progressBar.visibility = View.GONE
                apiState.msg.message?.let {
                    Snackbar.make(
                        binding.root,
                        it, Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}