package com.example.favouriteplayertracker.data.local.twitterData

import androidx.room.TypeConverter
import com.example.favouriteplayertracker.data.repository.tweets.SingleTweet
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class TweetConverter {

    private val gson = Gson()
    private val type = object : TypeToken<List<SingleTweet>>() {}.type

    @TypeConverter
    fun stringToTweet(json: String): List<SingleTweet> {
        return gson.fromJson(json, type)
    }

    @TypeConverter
    fun tweetToString(tweets: List<SingleTweet>): String {
        return gson.toJson(tweets, type)

    }

}