package com.example.favouriteplayertracker.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_player_list")
data class FavouritePlayer(
    @PrimaryKey @ColumnInfo() val name: String,
    @ColumnInfo() val selected: Boolean = false
    )