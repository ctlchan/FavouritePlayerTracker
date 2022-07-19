package com.example.favouriteplayertracker.data.local.seasonAverages

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "season_averages")
data class SeasonAverages(
    @PrimaryKey() val playerId: Int,
    val ast: Double,
    val blk: Double,
    val dreb: Double,
    val fg3_pct: Double,
    val fg3a: Double,
    val fg3m: Double,
    val fg_pct: Double,
    val fga: Double,
    val fgm: Double,
    val ft_pct: Double,
    val fta: Double,
    val ftm: Double,
    val games_played: Int,
    val min: String,
    val oreb: Double,
    val pf: Double,
    val player_id: Int,
    val pts: Double,
    val reb: Double,
    val season: Int,
    val stl: Double,
    val turnover: Double
)
