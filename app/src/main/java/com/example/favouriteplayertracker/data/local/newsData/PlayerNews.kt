package com.example.favouriteplayertracker.data.local.newsData

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.favouriteplayertracker.data.remote.newsApi.Article

@Entity(tableName = "articles")
data class PlayerNews(
    @PrimaryKey() val playerId: Int,
    @TypeConverters(ArticleConverter::class) val articles: List<Article>, // to be parsed on dao call
    val lastUpdated: String // of format: "yyyy-MM-dd HH:mm"
)
