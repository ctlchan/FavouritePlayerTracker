package com.example.favouriteplayertracker.data.local.twitterData

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.favouriteplayertracker.data.repository.tweets.SingleTweet


@Entity(tableName = "player_tweets")
data class Tweets(
    @PrimaryKey val playerName: String,
    @TypeConverters(TweetConverter::class) val tweets: List<SingleTweet>,
    val lastUpdated: String
)
