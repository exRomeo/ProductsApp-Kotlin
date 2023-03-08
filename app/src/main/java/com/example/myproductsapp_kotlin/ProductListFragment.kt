package com.example.myproductsapp_kotlin

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myproductsapp_kotlin.databinding.FragmentProductListBinding


class ProductListFragment : Fragment() {

    lateinit var binding: FragmentProductListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

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
        val adapter =
            if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) {

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
        adapter.submitList(makeList())
    }

    val onClick = { product: Product ->
        startActivity(
            Intent(
                this.context, SingleProductActivity::class.java
            ).putExtra("product", product)
        )
    }


    private fun makeList(): List<Product> {
        return listOf(
            Product(title = "itemOne", description = "Dummy data", drawable = R.drawable.one),
            Product(title = "ItemTwo", description = "Dummy data", drawable = R.drawable.two),
            Product(title = "ItemThree", description = "Dummy data", drawable = R.drawable.three),
            Product(title = "ItemFour", description = "Dummy data", drawable = R.drawable.four),
            Product(title = "ItemFive", description = "Dummy data", drawable = R.drawable.five)
        )
    }
}