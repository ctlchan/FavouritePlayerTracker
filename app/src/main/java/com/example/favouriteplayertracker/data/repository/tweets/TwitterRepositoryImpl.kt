package com.example.favouriteplayertracker.data.repository.tweets

import android.util.Log
import androidx.lifecycle.LiveData
import io.github.redouane59.twitter.TwitterClient
import io.github.redouane59.twitter.dto.tweet.TweetList

class TwitterRepositoryImpl(
    private val twitterClient: TwitterClient
): TwitterRepository {

    private val TAG = "TwitterRepositoryImpl"


    override suspend fun getPlayerTweets(playerName: String): TweetList {

        Log.i(TAG, "getPlayerTweets(${playerName})")

        return twitterClient.searchTweets(
            playerName
        )
    }
}