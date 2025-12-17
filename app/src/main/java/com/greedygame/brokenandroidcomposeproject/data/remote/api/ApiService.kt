package com.greedygame.brokenandroidcomposeproject.data.remote.api

import com.greedygame.brokenandroidcomposeproject.model.NewsResponse
import com.greedygame.brokenandroidcomposeproject.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("v2/everything")
    suspend fun getArticles(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String = Constants.API_KEY
    ): NewsResponse
}