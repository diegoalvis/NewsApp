package com.diegoalvis.android.newsapp.viewmodels

import android.content.Context
import android.databinding.ObservableInt
import android.view.View
import com.diegoalvis.android.newsapp.R
import com.diegoalvis.android.newsapp.api.ApiClient
import com.diegoalvis.android.newsapp.models.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ActivtyViewModel(context: Context) : BaseViewModel(context) {


    fun getArticles(): Observable<List<Article>> {
        return ApiClient
                .getInstance()
                .getNewsList(context.getString(R.string.home), context.getString(R.string.API_KEY))
                .map {response -> response.newsList}
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())

    }

}
