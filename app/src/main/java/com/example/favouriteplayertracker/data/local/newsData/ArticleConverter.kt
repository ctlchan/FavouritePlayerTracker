package com.example.favouriteplayertracker.data.local.newsData

import androidx.room.TypeConverter
import com.example.favouriteplayertracker.data.remote.newsApi.Article
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.lang.reflect.TypeVariable

class ArticleConverter {

    private val gson = Gson()
    private val type = object: TypeToken<List<Article>>(){}.type

    @TypeConverter
    fun stringToArticle(json: String): List<Article> {
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun articleToString(articles: List<Article>): String {
        return gson.toJson(articles, type)
    }

}