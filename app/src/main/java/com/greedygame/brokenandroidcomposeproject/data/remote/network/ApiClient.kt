package com.greedygame.brokenandroidcomposeproject.data.remote.network

import com.greedygame.brokenandroidcomposeproject.data.remote.api.ApiService
import com.greedygame.brokenandroidcomposeproject.utils.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiClient {
    val api: ApiService = Retrofit.Builder()
        .baseUrl(Constants.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}