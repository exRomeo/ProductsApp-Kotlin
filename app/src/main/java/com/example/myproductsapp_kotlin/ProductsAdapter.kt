package com.example.myproductsapp_kotlin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myproductsapp_kotlin.databinding.ListItemBinding

class ProductsAdapter(val onClick: (Product) -> Unit) :
    ListAdapter<Product, ProductsAdapter.ViewHolder>(ProductDiffUtil()) {
    lateinit var binding: ListItemBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        binding = ListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.binding.tvProductName.text = current.title
        holder.binding.tvPrice.text = current.getTextPrice()
        Glide.with(binding.root.context).load(current.thumbnail).into(holder.binding.ivImg)
        holder.binding.frgRatingBar.rating = current.rating
        holder.binding.card.setOnClickListener { onClick(current) }
    }

    inner class ViewHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)
}

class ProductDiffUtil :
    DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem == newItem
}