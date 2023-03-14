package com.example.myproductsapp_kotlin

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myproductsapp_kotlin.databinding.ListItemBinding
import com.example.myproductsapp_kotlin.repository.Product

class ProductsAdapter(
    private val onClick: OnProductClick,
    private val favoriteIcon: Drawable?,
    private val deleteIcon: Drawable?
) : ListAdapter<Product, ProductsAdapter.ViewHolder>(ProductDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: ListItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.list_item, parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.binding.product = current
        holder.binding.action = onClick
        holder.binding.favButton.setImageDrawable(if (current.isFavorite) deleteIcon else favoriteIcon)
    }

    inner class ViewHolder(var binding: ListItemBinding) : RecyclerView.ViewHolder(binding.root)
}

class ProductDiffUtil : DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.id == newItem.id && oldItem.isFavorite == newItem.isFavorite

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem == newItem
}