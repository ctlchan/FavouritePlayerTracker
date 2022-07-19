package com.example.favouriteplayertracker.data.local.seasonAverages

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SeasonAvgDao {

    @Query("SELECT * FROM season_averages")
    fun getSeasonAverages(): Flow<List<SeasonAverages>>

}