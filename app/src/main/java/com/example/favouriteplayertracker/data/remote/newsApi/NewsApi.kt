package com.example.favouriteplayertracker.data.remote.newsApi

import com.example.favouriteplayertracker.BuildConfig
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsApi {

    @Headers("x-rapidapi-key: ${BuildConfig.NEWS_API_KEY}",
        "X-RapidAPI-Host: free-news.p.rapidapi.com"
    )
    @GET("/v1/search")
    suspend fun getPlayerNews(@Query("q") name: String, @Query("lang") lang: String = "en"): Response<NewsJson>


}