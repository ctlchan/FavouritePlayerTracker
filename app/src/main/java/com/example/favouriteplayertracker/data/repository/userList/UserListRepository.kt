package com.example.favouriteplayertracker.data.repository.userList

import com.example.favouriteplayertracker.data.local.Teams.TeamEntity
import com.example.favouriteplayertracker.data.local.UserList.FavouritePlayer
import kotlinx.coroutines.flow.Flow

interface UserListRepository {

    // use a suspend function only when you need to access the API

    fun getFavouritePlayers(): Flow<List<FavouritePlayer>>

    suspend fun addFavouritePlayer(name: String)

    suspend fun addTeam(team: TeamEntity)

    suspend fun removeFavouritePlayer(player: FavouritePlayer)

    suspend fun unselectPlayer()

    suspend fun reselectPlayer(name: String)

    fun getSelected(): Flow<FavouritePlayer>

    fun getTeam(id: Int): Flow<TeamEntity>

}