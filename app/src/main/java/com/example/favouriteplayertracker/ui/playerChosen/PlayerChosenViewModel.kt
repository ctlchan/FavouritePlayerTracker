package com.example.favouriteplayertracker.ui.playerChosen

import android.util.Log
import androidx.lifecycle.*
import com.example.favouriteplayertracker.data.local.Teams.TeamEntity
import com.example.favouriteplayertracker.data.local.UserList.FavouritePlayer
import com.example.favouriteplayertracker.data.local.newsData.PlayerNews
import com.example.favouriteplayertracker.data.repository.playerNews.PlayerNewsRepository
import com.example.favouriteplayertracker.data.repository.userList.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PlayerChosenViewModel @Inject
    constructor(@Named("userListRepo") userListRepository: UserListRepository,
            @Named("newsRepo") playerNewsRepository: PlayerNewsRepository) : ViewModel() {

    private val TAG = "PlayerChosenViewModel"

    private val userListRepo = userListRepository
    private val newsRepo = playerNewsRepository


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

}

