package com.example.favouriteplayertracker.data.local.UserList

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface UserListDao {

    @Query("SELECT * FROM user_player_list")
    fun getAllPlayers(): Flow<List<FavouritePlayer>>

    @Query("SELECT name FROM user_player_list")
    suspend fun getPlayerNames(): List<String>

    @Query("SELECT EXISTS (SELECT 1 FROM user_player_list WHERE name = :name)")
    suspend fun playerExists(name: String): Boolean

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPlayer(player: FavouritePlayer)

    @Delete
    suspend fun deletePlayer(player: FavouritePlayer)

    @Query("DELETE FROM user_player_list")
    suspend fun deleteAllPlayers()

    @Query("UPDATE user_player_list SET selected=0")
    suspend fun unselect()

    @Query("UPDATE user_player_list SET selected=1 WHERE name = :name")
    suspend fun reselect(name: String)

    @Query("SELECT * FROM user_player_list WHERE selected=1")
    fun getSelected(): Flow<FavouritePlayer>

    @Query("SELECT id FROM user_player_list WHERE selected=1")
    suspend fun getSelectedId(): Int


}