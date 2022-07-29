package com.example.favouriteplayertracker.data.repository.tweets

data class SingleTweet(
    val text: String,
    val id: Long,
    val authorId: Long?,
    val createdAt: String
)
