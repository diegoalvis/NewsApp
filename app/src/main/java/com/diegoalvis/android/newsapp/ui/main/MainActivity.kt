package com.diegoalvis.android.newsapp.ui.main

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.widget.Toast

import com.diegoalvis.android.newsapp.R
import com.diegoalvis.android.newsapp.databinding.ActivityMainBinding
import com.diegoalvis.android.newsapp.models.Article
import com.diegoalvis.android.newsapp.ui.adapters.ArticlesAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy { MainViewModel(this) }
    private val articlesAdapter: ArticlesAdapter = ArticlesAdapter()
    private lateinit var binding: ActivityMainBinding
    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setRecyclerView()
    }

    private fun setRecyclerView() {
        recyclerView.adapter = articlesAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        subscriptions.add(articlesAdapter.articleSelected()
                .subscribe(this::itemSelected))
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.dispose()
    }

    fun itemSelected(article: Article) {}

    private fun loadData() {
        binding.isLoading = true
        subscriptions.add(viewModel.getArticles()
                .doOnTerminate{ binding.isLoading = false }
                .subscribeBy(
                        onNext = { articlesAdapter.items = it },
                        onError = {
                            it.printStackTrace()
                            Toast.makeText(this, "Connection error!", Toast.LENGTH_SHORT).show()
                        }
                ))
    }

}
