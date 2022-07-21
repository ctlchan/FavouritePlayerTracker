package com.example.favouriteplayertracker.data.repository.playerNews

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.asLiveData
import com.example.favouriteplayertracker.data.local.UserList.UserListDao
import com.example.favouriteplayertracker.data.local.newsData.PlayerNews
import com.example.favouriteplayertracker.data.local.newsData.NewsDao
import com.example.favouriteplayertracker.data.remote.newsApi.NewsApi
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.Duration


class PlayerNewsRepositoryImpl(
    private val userListDao: UserListDao,
    private val newsDao: NewsDao,
    private val newsApi: NewsApi
): PlayerNewsRepository {

    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    private val TAG = "NewsRepoImpl"

    override suspend fun getNews(playerId: Int): Flow<PlayerNews> {
        // Get news from the local cache - parsed in ViewModel
        Log.i(TAG, "getNews($playerId)")

        Log.i(TAG, "newsDao.getCount(): ${newsDao.getCount()}")

        val players = userListDao.getPlayerNames()
        val numNewsRows = newsDao.getCount()
        val numPlayerRows = players.size

        // No need to check if  PlayerList is also empty b/c it can't be to get to the news fragment
        if (numNewsRows == 0 || numNewsRows != numPlayerRows) {
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
            val lastUpdated = newsDao.getLastUpdated(playerId)
            val currentTime = LocalDateTime.now().format(formatter)

            val time1 = LocalDateTime.parse(lastUpdated, formatter)
            val time2 = LocalDateTime.parse(currentTime, formatter)

            val diffInMinutes = Duration.between(time1, time2).toMinutes()

            // Make an API call to update data every hour.
            if (diffInMinutes > 60) {
                // Find player's name
//                for (player in userListDao.getAllPlayers().asLiveData().value!!) {
//                    if (player.id == playerId.toLong()) {
//                        playerName = player.name
//                    }
//                }

                val idToNameMap: Map<Long, String> = userListDao.getIdToNameMap()
                Log.i(TAG, "Map: ${idToNameMap.toString()}")

                val playerName = idToNameMap[playerId.toLong()]
                Log.i(TAG, "player name from map: $playerName")

                if (playerName != null) {
                    storeFromNetwork(playerName)
                }

            }
        }

        return newsDao.getArticles(playerId)
    }

    override suspend fun storeFromNetwork(playerName: String) {
        Log.i(TAG, "storeFromNetwork($playerName)")

        val response = try {
            newsApi.getPlayerNews(playerName)
        }
        catch (e: IOException) {
            Log.e(TAG, "IOException")
            null
        }
        catch (e: HttpException) {
            Log.e(TAG, "HttpException")
            null
        }

        if (response != null) {

            if (response.isSuccessful && response.body() != null) {

                val data = response.body()?.articles


                Log.i(TAG, data.toString())

                if (data!= null) {


                    val newNews = PlayerNews(
                        playerId = userListDao.getSelectedId(),
                        articles = data,
                        lastUpdated = LocalDateTime.now().format(formatter)
                    )
                    newsDao.addArticles(newNews)

                    }
                }

            }

        }

}

