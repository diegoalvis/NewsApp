package com.diegoalvis.android.newsapp.utils

import android.databinding.BindingAdapter
import android.net.Uri
import android.widget.ImageView
import com.squareup.picasso.Picasso

object Attrs{
    @BindingAdapter("app:imgUrl")
    @JvmStatic
    fun loadImage(imageView: ImageView, url: String?) {
        if (!url.isNullOrEmpty()) {
            Picasso.with(imageView.context)
                    .load(Uri.parse(url))
                    .into(imageView)
        }
    }
}