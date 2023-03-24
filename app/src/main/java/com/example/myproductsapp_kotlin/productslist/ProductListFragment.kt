package com.example.myproductsapp_kotlin.productslist

import android.content.Intent
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
import com.example.myproductsapp_kotlin.databinding.FragmentProductListBinding
import com.example.myproductsapp_kotlin.repository.Product
import com.example.myproductsapp_kotlin.repository.Repository
import com.example.myproductsapp_kotlin.singleproduct.SingleProductActivity

class ProductListFragment : Fragment(), OnProductClick {

    private lateinit var binding: FragmentProductListBinding
    private lateinit var adapter: ProductsAdapter
    private val repository by lazy { Repository(this.requireContext()) }
    private val viewModel: ProductListViewModel by lazy { ViewModelProvider(
        this.requireActivity(), ProductListViewModelFactory(repository)
    )[(ProductListViewModel::class.java)] }

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
        viewModel.updateList()
        viewModel.productsList.observe(this.requireActivity()) {
            adapter.submitList(it)
        }
    }

    private fun iniRecyclerView() {
        adapter = ProductsAdapter(
            this,
            ContextCompat.getDrawable(this.requireContext(), R.drawable.heart),
            ContextCompat.getDrawable(this.requireContext(), R.drawable.trash)
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
}