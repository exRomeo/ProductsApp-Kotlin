package com.example.myproductsapp_kotlin

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

class MyBindingUtils {
}
@BindingAdapter("imageUrl")
fun loadImage(view:ImageView, url:String){
    Glide.with(view).load(url).into(view)
}