package com.lufthansa.domain.repository

import com.lufthansa.domain.model.Gif

interface GiphyRepository {
    suspend fun getTrending(limit: Int = 25, offset: Int = 0): Result<List<Gif>>
    suspend fun search(query: String, limit: Int = 25, offset: Int = 0): Result<List<Gif>>
}