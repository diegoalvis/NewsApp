package com.diegoalvis.android.newsapp

import android.app.Application
import com.androidnetworking.AndroidNetworking

class NewsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidNetworking.initialize(applicationContext)
    }
}