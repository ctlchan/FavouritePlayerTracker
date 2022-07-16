package com.example.favouriteplayertracker.data.remote

data class NbaApiGetAllJson(
    val data: List<Map<String, Any>>,
    val meta: Map<String, Any>
)