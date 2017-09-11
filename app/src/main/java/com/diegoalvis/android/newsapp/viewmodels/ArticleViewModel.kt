package com.diegoalvis.android.newsapp.viewmodels

import android.content.Context
import android.databinding.BindingAdapter
import android.widget.ImageView
import com.diegoalvis.android.newsapp.models.Article
import com.squareup.picasso.Picasso


class ArticleViewModel(context: Context, article: Article): BaseViewModel(context) {

    var article: Article = article


    fun getImageUrl(): String? = this.article?.multimedia[2]!!.url

    fun loadImage(view: ImageView) = Picasso.with(context).load(getImageUrl()).into(view)

}