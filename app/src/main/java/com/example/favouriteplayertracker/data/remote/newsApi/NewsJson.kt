package com.example.favouriteplayertracker.data.remote.newsApi

data class NewsJson(
    val articles: List<Article>,
    val page: Int,
    val page_size: Int,
    val status: String,
    val total_hits: Int,
    val total_pages: Int,
    val user_input: UserInput
)