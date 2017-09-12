package com.diegoalvis.android.newsapp.models

data class Multimedia(val url: String)
data class Article(val section: String, val title: String, val url: String, val multimedia: List<Multimedia>)




