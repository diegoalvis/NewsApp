package com.diegoalvis.android.newsapp.ui.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.diegoalvis.android.newsapp.R
import com.diegoalvis.android.newsapp.databinding.ItemArticleBinding
import com.diegoalvis.android.newsapp.models.Article
import com.diegoalvis.android.newsapp.utils.inflate
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject

class ArticlesAdapter : RecyclerView.Adapter<ArticlesAdapter.ArticleViewHolder>() {

    val articleSelected = PublishSubject.create<Article>()
    var items: List<Article> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder
            = ArticleViewHolder(parent.inflate(R.layout.item_article))

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.binding.article = items[position]
        holder.binding.selected = articleSelected
    }

    override fun getItemCount() = items.size

    fun articleSelected(): Observable<Article> = articleSelected

    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var binding: ItemArticleBinding = DataBindingUtil.bind(itemView)
    }

}