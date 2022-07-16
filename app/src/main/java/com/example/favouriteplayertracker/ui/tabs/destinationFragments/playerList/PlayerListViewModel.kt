package com.example.favouriteplayertracker.ui.tabs.destinationFragments.playerList

import androidx.lifecycle.*
import com.example.favouriteplayertracker.data.repository.userList.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named


@HiltViewModel
class PlayerListViewModel @Inject constructor(
    @Named("userListRepo") userListRepository: UserListRepository,
): ViewModel() {

    private val userListRepo = userListRepository

//    private val _playerList = MutableLiveData<List<FavouritePlayer>>()
//    val playerList: LiveData<List<FavouritePlayer>>
//        get() = _playerList
//
//    fun updateList() {
//        _playerList.value = repo.getFavouritePlayers().asLiveData().value
//    }

    val userList = userListRepo.getFavouritePlayers().asLiveData()

    fun selectPlayer(name: String) {
        viewModelScope.launch(Dispatchers.Main) {
            userListRepo.unselectPlayer()
            userListRepo.reselectPlayer(name)


            // TODO: geta a better understanding of coroutines to fix issue with selected player not appearing.
        }
    }



}