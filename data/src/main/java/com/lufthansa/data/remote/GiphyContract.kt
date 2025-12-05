package com.lufthansa.data.remote

import com.lufthansa.data.dto.GiphyResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface GiphyContract {
    @GET("v1/gifs/trending")
    suspend fun trending(
        @Query("api_key") apiKey: String,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0
    ): GiphyResponse

    @GET("v1/gifs/search")
    suspend fun search(
        @Query("api_key") apiKey: String,
        @Query("q") query: String,
        @Query("limit") limit: Int = 25,
        @Query("offset") offset: Int = 0
    ): GiphyResponse
}