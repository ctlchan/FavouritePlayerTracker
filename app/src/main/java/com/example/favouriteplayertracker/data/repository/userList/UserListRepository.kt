package com.example.favouriteplayertracker.data.repository.userList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import com.example.favouriteplayertracker.data.local.FavouritePlayer
import com.example.favouriteplayertracker.data.local.LocalDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

interface UserListRepository {

    // use a suspend function only when you need to access the API

    fun getFavouritePlayers(): Flow<List<FavouritePlayer>>

    suspend fun addFavouritePlayer(name: String)

    suspend fun removeFavouritePlayer(player: FavouritePlayer)

    suspend fun unselectPlayer()

    suspend fun reselectPlayer(name: String)

    fun getSelected(): Flow<String>

}