package com.diegoalvis.android.newsapp.api

import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    val BASE_URL: String = "asdasd"

    private var retrofit: Retrofit? = null

    @Synchronized private fun createInstance() {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                        .build()
        }
    }

    fun getInstance(): Retrofit? {
        if (retrofit == null) createInstance()
        return retrofit
    }
}