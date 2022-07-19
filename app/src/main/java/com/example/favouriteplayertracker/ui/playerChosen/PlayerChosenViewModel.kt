package com.example.favouriteplayertracker.ui.playerChosen

import androidx.lifecycle.*
import com.example.favouriteplayertracker.data.local.Teams.TeamEntity
import com.example.favouriteplayertracker.data.local.UserList.FavouritePlayer
import com.example.favouriteplayertracker.data.repository.userList.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PlayerChosenViewModel @Inject
    constructor(@Named("userListRepo") userListRepository: UserListRepository) : ViewModel() {

    private val TAG = "PlayerChosenViewModel"

    private val userListRepo = userListRepository

    fun getSelected(): LiveData<FavouritePlayer> {

        return userListRepo.getSelected().asLiveData()
    }

    fun unselect() {
        viewModelScope.launch {
            userListRepo.unselectPlayer()
        }
    }


    fun getPlayerIds(): LiveData<List<Int>> {
        return userListRepo.getPlayerIds().asLiveData()
    }


    fun getTeamInfo(teamId: Int): LiveData<TeamEntity> {
        return userListRepo.getTeam(teamId).asLiveData()
    }

}

