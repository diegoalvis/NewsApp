package com.diegoalvis.android.newsapp.ui

import android.databinding.DataBindingUtil
import android.databinding.ObservableBoolean
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast
import com.android.databinding.library.baseAdapters.BR
import com.diegoalvis.android.newsapp.R
import com.diegoalvis.android.newsapp.databinding.ActivityMainBinding
import com.diegoalvis.android.newsapp.models.Article
import com.diegoalvis.android.newsapp.ui.adapters.ArticlesAdapter
import com.diegoalvis.android.newsapp.viewmodels.ActivtyViewModel
import com.diegoalvis.android.newsapp.viewmodels.ArticleViewModel
import io.reactivex.disposables.CompositeDisposable
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: ActivtyViewModel
    lateinit var articlesAdapter: ArticlesAdapter
    lateinit var binding: ActivityMainBinding

    var subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ActivtyViewModel(this)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.executePendingBindings()
        setRecyclerView()
    }

    private fun setRecyclerView() {
        articlesAdapter = ArticlesAdapter(ArrayList<Article>(), this)
        subscriptions.add(articlesAdapter.articleSelected().subscribe(this::itemSelected))
        binding?.recyclerView?.setLayoutManager(GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false))
    }

    fun itemSelected(articleViewModel: ArticleViewModel) {
        TODO("Implemntar logica para renderizar el fragment de detalle")
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.dispose()
    }


    private fun loadData() {
        binding.isLoading = true
        subscriptions.add(viewModel.getArticles()
                .doOnNext(this::addArticleToList)
                .doOnTerminate({ binding.isLoading = false })
                .doOnError({ Toast.makeText(this, "Connection error!", Toast.LENGTH_SHORT).show() })
                .subscribe(this::updateRecyclerView, Throwable::printStackTrace))
    }

    private fun addArticleToList(articleList: List<Article>) {
        for (article in articleList)
            articlesAdapter.addItem(article)
    }

    private fun updateRecyclerView(articleList: List<Article>) {
        binding?.recyclerView?.setAdapter(articlesAdapter)
        articlesAdapter.notifyDataSetChanged()
    }

}

