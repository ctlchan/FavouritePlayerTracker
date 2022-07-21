package com.example.favouriteplayertracker.data.repository.playerNews

import com.example.favouriteplayertracker.data.local.newsData.PlayerNews
import kotlinx.coroutines.flow.Flow

interface PlayerNewsRepository {

    suspend fun getNews(playerId: Int): Flow<PlayerNews>

    suspend fun storeFromNetwork(playerName: String)

}