package com.example.favouriteplayertracker.data.repository.tweets

import androidx.lifecycle.LiveData
import io.github.redouane59.twitter.dto.tweet.TweetList

interface TwitterRepository {

    suspend fun getPlayerTweets(playerName: String): TweetList

}