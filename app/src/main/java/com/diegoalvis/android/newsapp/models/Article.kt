package com.diegoalvis.android.newsapp.models


class Article(val section: String, val title: String, val url: String, val multimedia: List<Multimedia>) {
    class Multimedia(val url: String)
}


