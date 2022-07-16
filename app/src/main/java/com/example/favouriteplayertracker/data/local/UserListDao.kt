package com.example.favouriteplayertracker.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserListDao {

    @Query("SELECT * FROM user_player_list")
    fun getAllPlayers(): Flow<List<FavouritePlayer>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addPlayer(player: FavouritePlayer)
    // TODO: revert to a suspend function after moving add player logic to view model

    @Delete
    suspend fun deletePlayer(player: FavouritePlayer)

    @Query("DELETE FROM user_player_list")
    suspend fun deleteAllPlayers()

    @Query("UPDATE user_player_list SET selected=0")
    suspend fun unselect()

    @Query("UPDATE user_player_list SET selected=1 WHERE name = :name")
    suspend fun reselect(name: String)

    @Query("SELECT name FROM user_player_list WHERE selected=1")
    fun getSelected(): Flow<String>


}