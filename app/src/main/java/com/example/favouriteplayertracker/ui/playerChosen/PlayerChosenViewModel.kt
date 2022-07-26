package com.example.favouriteplayertracker.ui.playerChosen

import android.util.Log
import androidx.lifecycle.*
import com.example.favouriteplayertracker.data.local.Teams.TeamEntity
import com.example.favouriteplayertracker.data.local.UserList.FavouritePlayer
import com.example.favouriteplayertracker.data.local.newsData.PlayerNews
import com.example.favouriteplayertracker.data.repository.playerNews.PlayerNewsRepository
import com.example.favouriteplayertracker.data.repository.tweets.TwitterRepository
import com.example.favouriteplayertracker.data.repository.userList.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.redouane59.twitter.dto.tweet.TweetList
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

    suspend fun getTweets(): TweetList? {
        val playerMap = userListRepo.getIdToNameMap()
        val selectedId = userListRepo.getSelectedId()

        return playerMap[selectedId.toLong()]?.let { twitterRepo.getPlayerTweets(it) }

    }

}

