package com.example.myproductsapp_kotlin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.cardview.widget.CardView
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
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = getItem(position)
        holder.name.text = current.title
        holder.price.text = current.getTextPrice()
        Glide.with(binding.root.context).load(current.thumbnail).into(holder.img)
        holder.ratingBar.rating = current.rating
        holder.cardView.setOnClickListener { onClick(current) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = this@ProductsAdapter.binding.tvProductName
        var price: TextView = this@ProductsAdapter.binding.tvPrice
        var img: ImageView = this@ProductsAdapter.binding.ivImg
        var ratingBar: RatingBar = this@ProductsAdapter.binding.frgRatingBar
        var cardView: CardView = this@ProductsAdapter.binding.card
    }
}

class ProductDiffUtil :
    DiffUtil.ItemCallback<Product>() {

    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
        oldItem == newItem
}