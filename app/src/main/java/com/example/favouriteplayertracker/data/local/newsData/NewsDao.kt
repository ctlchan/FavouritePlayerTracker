package com.example.favouriteplayertracker.data.local.newsData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsDao {

    @Query("SELECT * FROM articles WHERE playerId = :id")
    fun getArticles(id: Int): Flow<PlayerNews>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addArticles(articles: PlayerNews)

    @Query("SELECT lastUpdated FROM articles WHERE playerId = :id")
    suspend fun getLastUpdated(id: Int): String

    @Query("SELECT count(*) FROM articles")
    suspend fun getCount(): Int // Get count to check if empty

}