package com.example.favouriteplayertracker.data.remote.nbaApi

data class NBAPlayer(
    val first_name: String,
    val height_feet: Int,
    val height_inches: Int,
    val id: Int,
    val last_name: String,
    val position: String,
    val team: Team,
    val weight_pounds: Int
)