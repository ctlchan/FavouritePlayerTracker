package com.example.favouriteplayertracker.ui.addPlayer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.favouriteplayertracker.data.repository.userList.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class AddPlayerViewModel @Inject constructor(
    @Named("userListRepo") userListRepository: UserListRepository): ViewModel() {

    private val userListRepo = userListRepository

    fun addPlayer(name: String) {

        viewModelScope.launch {
            userListRepo.addFavouritePlayer(name)
        }

    }

}