package com.example.favouriteplayertracker.data.remote.newsApi

data class NewsJson(
    val status: String,
    val total_hits: Int,
    val page: Int,
    val total_pages: Int,
    val page_size: Int,
    val articles: List<String>, // Really is List<Map<String, *>>   (List<JSON>)
    val user_input: String      // Really is Map<String, *>         (JSON)

)
