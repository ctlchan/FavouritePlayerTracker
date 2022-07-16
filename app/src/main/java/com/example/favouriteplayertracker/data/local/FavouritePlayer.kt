package com.example.favouriteplayertracker.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.favouriteplayertracker.data.remote.Team

@Entity(tableName = "user_player_list")
data class FavouritePlayer(
    @PrimaryKey val name: String,
    val height_feet: Int?,
    val height_inches: Int?,
    val id: Long,
    val position: String,
    val team_name: String,
    val weight_pounds: Int?,
    var selected: Boolean = false
)