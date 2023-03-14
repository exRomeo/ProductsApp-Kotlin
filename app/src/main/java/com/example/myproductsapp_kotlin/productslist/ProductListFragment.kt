package com.example.myproductsapp_kotlin.productslist

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproductsapp_kotlin.OnProductClick
import com.example.myproductsapp_kotlin.ProductsAdapter
import com.example.myproductsapp_kotlin.R
import com.example.myproductsapp_kotlin.singleproduct.SingleProductActivity
import com.example.myproductsapp_kotlin.databinding.FragmentProductListBinding
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository

class ProductListFragment : Fragment(), OnProductClick {

    private lateinit var binding: FragmentProductListBinding
    private lateinit var adapter: ProductsAdapter
    private lateinit var viewModel: ProductListViewModel

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
        viewModel = ViewModelProvider(
            this.requireActivity(),
            ProductListViewModelFactory(repository)
        )[(ProductListViewModel::class.java)]
        if (viewModel.checkConnection()) {
            viewModel.getOnlineList()
            viewModel.productsList.observe(this.requireActivity()) {
                adapter.submitList(it)
            }
        } else {
            viewModel.getOfflineList()
            viewModel.productsList.observe(this.requireActivity()) {
                adapter.submitList(it)
            }
        }
    }


    private fun iniRecyclerView() {
        adapter = ProductsAdapter(this, ContextCompat.getDrawable(this.requireContext(), R.drawable.heart))
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
    override fun onFavoriteClick(product: Product) {
        viewModel.addFavorite(product)
    }
}