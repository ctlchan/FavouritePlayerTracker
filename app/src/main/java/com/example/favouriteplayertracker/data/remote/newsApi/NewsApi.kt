package com.example.favouriteplayertracker.data.remote.newsApi

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("/v1/search")
    fun getPlayerNews(@Query("q") name: String, @Query("lang") lang: String = "en"): Response<NewsJson>


}