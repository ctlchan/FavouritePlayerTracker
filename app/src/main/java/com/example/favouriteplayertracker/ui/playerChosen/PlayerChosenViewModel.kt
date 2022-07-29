package com.example.favouriteplayertracker.ui.playerChosen

import android.os.HandlerThread
import android.os.Looper
import android.util.Log
import androidx.lifecycle.*
import com.example.favouriteplayertracker.data.local.Teams.TeamEntity
import com.example.favouriteplayertracker.data.local.UserList.FavouritePlayer
import com.example.favouriteplayertracker.data.local.newsData.PlayerNews
import com.example.favouriteplayertracker.data.local.twitterData.Tweets
import com.example.favouriteplayertracker.data.repository.playerNews.PlayerNewsRepository
import com.example.favouriteplayertracker.data.repository.tweets.TwitterRepository
import com.example.favouriteplayertracker.data.repository.userList.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import okhttp3.internal.wait

import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PlayerChosenViewModel @Inject
    constructor(@Named("userListRepo") userListRepository: UserListRepository,
            @Named("newsRepo") playerNewsRepository: PlayerNewsRepository,
            @Named("tweetRepo") twitterRepository: TwitterRepository) : ViewModel() {

    private val TAG = "PlayerChosenViewModel"

    private val userListRepo = userListRepository
    private val newsRepo = playerNewsRepository
    private val twitterRepo = twitterRepository

    private lateinit var playerMap: Map<Long, String>
    private var selectedId: Long = -1
    private var playerName: String = ""





    fun getSelected(): LiveData<FavouritePlayer> {

        return userListRepo.getSelected().asLiveData()
    }

    fun unselect() {
        viewModelScope.launch {
            userListRepo.unselectPlayer()
        }
    }



    suspend fun getNews(): LiveData<PlayerNews> {

        val id = userListRepo.getSelectedId()
        Log.i(TAG, id.toString())
        return newsRepo.getNews(id).asLiveData()
    }

    fun getTeamInfo(teamId: Int): LiveData<TeamEntity> {
        return userListRepo.getTeam(teamId).asLiveData()
    }

    private suspend fun loadTweets() {
        playerMap = userListRepo.getIdToNameMap()
        selectedId = userListRepo.getSelectedId().toLong()
        playerName = playerMap[selectedId].toString()

        Log.i(TAG, "playerName = $playerName")

        twitterRepo.refreshTweets(playerName)
    }

    suspend fun getTweets(): LiveData<Tweets> {

        viewModelScope.launch(Dispatchers.IO) {
            loadTweets()
        }

        return withContext(Dispatchers.Main) {

            // TODO: IT WORKED, TEST TO MAKE SURE IT STILL WORKS, ADJUST delay() IF NECESSARY
            delay(550)
            twitterRepo.getTweets(playerName).asLiveData()
        }


//        return twitterRepo.getTweets(playerName).asLiveData()

    }

}

