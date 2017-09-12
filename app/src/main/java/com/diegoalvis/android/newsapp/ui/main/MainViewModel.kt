package com.diegoalvis.android.newsapp.ui.main

import android.content.Context
import com.diegoalvis.android.newsapp.R
import com.diegoalvis.android.newsapp.api.ApiClient
import com.diegoalvis.android.newsapp.api.ApiInterface
import com.diegoalvis.android.newsapp.models.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers

class MainViewModel(private val context: Context){

    private val newsApi:ApiInterface = ApiClient.getInstance()

    fun getArticles(): Observable<List<Article>> {
        return newsApi
                .getNewsList(context.getString(R.string.home), context.getString(R.string.API_KEY))
                .map {response -> response.newsList}
                .observeOn(AndroidSchedulers.mainThread())
    }

}
