package com.diegoalvis.android.newsapp.ui.adapters

import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.Adapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.diegoalvis.android.newsapp.R
import com.diegoalvis.android.newsapp.databinding.ItemArticleBinding
import com.diegoalvis.android.newsapp.models.Article
import com.diegoalvis.android.newsapp.viewmodels.ArticleViewModel
import com.jakewharton.rxbinding2.view.RxView
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject


class ArticlesAdapter: Adapter<ArticlesAdapter.ArticleViewHolder> {

    private var context: Context

    var articleSelected = PublishSubject.create<ArticleViewModel>()
    var items: ArrayList<Article>


    constructor(items: List<Article>, context: Context) {
        this.items = items as ArrayList<Article>
        this.context = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        val v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_article, parent, false)
        return ArticleViewHolder(v)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) = holder.bind(ArticleViewModel(context, items[position]), articleSelected)

    override fun getItemCount() = items.size



    fun articleSelected(): Observable<ArticleViewModel> {
        return articleSelected.hide()
    }

    fun addItem(article: Article) {
        this.items.add(article)
        notifyDataSetChanged()
    }


    class ArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageSelectedSubscription: Disposable? = null
        var binding: ItemArticleBinding = DataBindingUtil.bind(itemView)

        fun bind(viewModel: ArticleViewModel, articleSelected: PublishSubject<ArticleViewModel>) = with(itemView) {
            binding.setArticleViewModel(viewModel)
            viewModel.loadImage(binding.articleImage)
            manageSubscriptions(viewModel, articleSelected)
        }

        private fun manageSubscriptions(viewModel: ArticleViewModel, articleSelected: PublishSubject<ArticleViewModel>) {
            if (imageSelectedSubscription != null && !imageSelectedSubscription!!.isDisposed()) {
                imageSelectedSubscription!!.dispose()
            }
            imageSelectedSubscription = RxView.clicks(binding.cardView).subscribe({ articleSelected.onNext(viewModel) })
//            subscriptions.add(imageSelectedSubscription)

        }
    }
}