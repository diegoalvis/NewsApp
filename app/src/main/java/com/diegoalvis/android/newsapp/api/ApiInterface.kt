package com.diegoalvis.android.newsapp.api

import io.reactivex.Observable
import retrofit2.http.GET


interface ApiInterface {

    @GET("/news")
    fun getNewsList(): Observable<Object>
}