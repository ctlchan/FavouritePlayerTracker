package com.example.favouriteplayertracker.data.local.Teams

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.favouriteplayertracker.data.remote.Team
import kotlinx.coroutines.flow.Flow

@Dao
interface TeamDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTeam(team: TeamEntity)

    @Query("SELECT * FROM teams WHERE id = :id")
    fun getTeam(id: Int): Flow<TeamEntity>

}