package com.example.favouriteplayertracker.ui.playerChosen

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.favouriteplayertracker.data.repository.userList.UserListRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class PlayerChosenViewModel @Inject
    constructor(@Named("userListRepo") userListRepository: UserListRepository) : ViewModel() {

    private val userListRepo = userListRepository

    fun getSelected(): LiveData<String> {

        val selected = userListRepo.getSelected().asLiveData()

        return selected
    }

    fun unselect() {
        viewModelScope.launch {
            userListRepo.unselectPlayer()
        }
    }

    val testString: String = "Hello there."

}