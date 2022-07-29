package com.example.favouriteplayertracker.data.local.twitterData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TweetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTweets(tweets: Tweets)

    @Query("SELECT * FROM player_tweets WHERE playerName = :playerName")
    fun getTweets(playerName: String): Flow<Tweets>

    @Query("SELECT lastUpdated FROM player_tweets WHERE playerName = :playerName")
    suspend fun getLastUpdated(playerName: String): String

    @Query("SELECT count(*) FROM player_tweets")
    suspend fun getCount(): Int


}