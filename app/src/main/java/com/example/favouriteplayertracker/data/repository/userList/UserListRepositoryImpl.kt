package com.example.favouriteplayertracker.data.repository.userList

import com.example.favouriteplayertracker.data.local.FavouritePlayer
import com.example.favouriteplayertracker.data.local.UserListDao
import kotlinx.coroutines.flow.Flow

class UserListRepositoryImpl(
    private val dao: UserListDao
): UserListRepository {

    override fun getFavouritePlayers(): Flow<List<FavouritePlayer>> {
        return dao.getAllPlayers()
    }

    override suspend fun addFavouritePlayer(player: FavouritePlayer) {
        dao.addPlayer(player)
    }

    override suspend fun removeFavouritePlayer(player: FavouritePlayer) {
            dao.deletePlayer(player)
    }

    override suspend fun unselectPlayer() {
        dao.unselect()
    }

    override suspend fun reselectPlayer(name: String) {
        dao.reselect(name)
    }

    override fun getSelected(): Flow<String> {
        return dao.getSelected()
    }
}