package com.example.favouriteplayertracker.data.remote

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NbaApi {

    @GET("/api/v1/players")
    suspend fun getPlayer(@Query("search") name: String): Response<NbaApiGetAllJson>

}