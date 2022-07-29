package com.example.favouriteplayertracker.data.repository.tweets

import androidx.lifecycle.LiveData
import com.example.favouriteplayertracker.data.local.twitterData.Tweets
import kotlinx.coroutines.flow.Flow


interface TwitterRepository {

    suspend fun getTweets(playerName: String): Flow<Tweets>

    suspend fun refreshTweets(playerName: String)

    suspend fun storeFromNetwork(playerName: String)

}