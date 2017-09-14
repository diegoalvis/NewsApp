package com.diegoalvis.android.newsapp.ui.main

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.diegoalvis.android.newsapp.R
import com.diegoalvis.android.newsapp.databinding.ActivityMainBinding
import com.diegoalvis.android.newsapp.models.Article
import com.diegoalvis.android.newsapp.ui.adapters.ArticlesAdapter
import com.diegoalvis.android.newsapp.ui.adapters.SectionAdapter
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by lazy { MainViewModel(this) }
    private val sectionAdapter: SectionAdapter by lazy { SectionAdapter(resources.getStringArray(R.array.sections)) }
    private val articlesAdapter: ArticlesAdapter = ArticlesAdapter()

    private val subscriptions = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding: ActivityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.viewModel = viewModel

        recyclerArticle.adapter = articlesAdapter
        recyclerSection.adapter = sectionAdapter

        setSubscriptions()
    }

    private fun setSubscriptions() {
        subscriptions.addAll(
                    articlesAdapter.articleSelected().subscribe(this::itemSelected),
                    sectionAdapter.sectionSelected().subscribe(this::sectionSelected)
        )
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        subscriptions.dispose()
    }

    fun sectionSelected(section: String) {
        loadData(section)
    }

    fun itemSelected(article: Article) {
        Toast.makeText(this, article.title.plus(" selected"), Toast.LENGTH_SHORT).show()
    }

    private fun loadData(section: String) {
        subscriptions.add(viewModel.getArticles(section)
                .subscribeBy(
                        onNext = { articlesAdapter.items = it },
                        onError = {
                            it.printStackTrace()
                            Toast.makeText(this, "Connection error!", Toast.LENGTH_SHORT).show()
                        }
                ))
    }

}
