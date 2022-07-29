package com.example.favouriteplayertracker.data.repository.tweets

import android.util.Log
import com.example.favouriteplayertracker.data.local.UserList.UserListDao
import com.example.favouriteplayertracker.data.local.twitterData.TweetDao
import com.example.favouriteplayertracker.data.local.twitterData.Tweets
import com.twitter.clientlib.ApiException
import com.twitter.clientlib.api.TwitterApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.collections.HashSet

class TwitterRepositoryImpl(
    private val twitterApi: TwitterApi,
    private val tweetDao: TweetDao,
    private val userListDao: UserListDao): TwitterRepository {

    private val TAG = "TwitterRepositoryImpl"
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")


    override suspend fun getTweets(playerName: String): Flow<Tweets> {
        Log.i(TAG, "getTweets($playerName)")
        val result = tweetDao.getTweets(playerName)

        Log.i(TAG, result.toString())

        return result
    }

    override suspend fun refreshTweets(playerName: String) {
        // Get tweets from the local cache - parsed in ViewModel

        Log.i(TAG, "refreshTweets($playerName)")

        Log.i(TAG, "tweetDao.getCount(): ${tweetDao.getCount()}")

        val players = userListDao.getPlayerNames()
        val numTweetsRows = tweetDao.getCount()
        val numPlayerRows = players.size

        // No need to check if  PlayerList is also empty b/c it can't be to get to the news fragment
        if (numTweetsRows == 0 || numTweetsRows != numPlayerRows) {
            // Case: no news has been stored or there is desync in the saved players and the news
            // stored about them.

            Log.i(TAG, "Updating entire player List")
            Log.i(TAG, players.toString())
            for (player in players) {
                storeFromNetwork(player)
            }
        }

        // else: check if need to update
        else {
            val lastUpdated = tweetDao.getLastUpdated(playerName)
            val currentTime = LocalDateTime.now().format(formatter)

            val time1 = LocalDateTime.parse(lastUpdated, formatter)
            val time2 = LocalDateTime.parse(currentTime, formatter)

            val diffInMinutes = Duration.between(time1, time2).toMinutes()
            Log.i(TAG, "diffInMinutes: $diffInMinutes")

            // Make an API call to update data every hour.
            if (diffInMinutes > 5) {
                // Find player's name
//                for (player in userListDao.getAllPlayers().asLiveData().value!!) {
//                    if (player.id == playerId.toLong()) {
//                        playerName = player.name
//                    }
//                }

                storeFromNetwork(playerName)

            }
    }
        val result = tweetDao.getTweets(playerName)

        Log.i(TAG, "tweet dao result: ${result.first()}")
    }

    override suspend fun storeFromNetwork(playerName: String) {

        Log.i(TAG, "storeFromNetwork($playerName)")

        val tweetFields: MutableSet<String> = HashSet()
        tweetFields.add("created_at")
        tweetFields.add("author_id")
        tweetFields.add("lang")


        val response = try{
             twitterApi.tweets().tweetsRecentSearch(playerName)
                 .maxResults(10)
                 .tweetFields(tweetFields)
                 .execute()
        }
        catch (e: ApiException) {
            Log.e(TAG, "Exception when calling TweetsApi#tweetsRecentSearch")
            Log.e(TAG, "Status code: ${e.code}")
            Log.e(TAG, "Reason: ${e.responseBody}")
            Log.e(TAG, "Response Headers: ${e.responseHeaders}")
            Log.e(TAG, e.stackTraceToString())
            null
        }

        if (response != null && response.errors == null) {

            val data = response.data

            val toBeStored: MutableList<SingleTweet> = mutableListOf()

            if (data != null) {
                for (tweet in data) {
                    if (tweet.lang == "en") {
                        Log.i(TAG, "tweet.lang == 'en'")

                        val createdAtFormatted = when (tweet.createdAt) {
                            null -> ""
                            else -> {
                                formatter.format(tweet.createdAt)
                            }
                        }


                        toBeStored.add(
                            SingleTweet(
                                text = tweet.text,
                                id = tweet.id.toLong(),
                                createdAt = createdAtFormatted,
                                authorId = tweet.authorId?.toLongOrNull()
                                )
                        )

                    }
                }

                Log.i(TAG, "toBeStored: $toBeStored")

                val newTweets = Tweets(
                    playerName = playerName,
                    tweets = toBeStored,
                    lastUpdated = LocalDateTime.now().format(formatter)
                )

                tweetDao.addTweets(newTweets)

            }

        }





    }
}