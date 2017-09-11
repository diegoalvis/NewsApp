package com.diegoalvis.android.newsapp.viewmodels

import android.content.Context
import android.databinding.BaseObservable

open class BaseViewModel: BaseObservable {

    var context: Context

    constructor(context: Context) {
        this.context = context
    }

}

