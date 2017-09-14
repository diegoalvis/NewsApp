package com.diegoalvis.android.newsapp.ui.main

import android.content.Context
import android.databinding.ObservableBoolean
import com.diegoalvis.android.newsapp.R
import com.diegoalvis.android.newsapp.api.ApiClient
import com.diegoalvis.android.newsapp.api.ApiInterface
import com.diegoalvis.android.newsapp.models.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(private val context: Context){

    var isLoading: ObservableBoolean = ObservableBoolean(false)

    private val newsApi: ApiInterface = ApiClient.getInstance()

    fun getArticles(section: String): Observable<List<Article>> {
        isLoading.set(true)
        return newsApi
                .getNewsList(section, context.getString(R.string.API_KEY))
                .doOnTerminate { isLoading.set(false) }
                .map {response -> response.newsList}
                .observeOn(AndroidSchedulers.mainThread())
    }
}
