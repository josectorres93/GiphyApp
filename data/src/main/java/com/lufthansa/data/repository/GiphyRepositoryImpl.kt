package com.lufthansa.data.repository

import com.lufthansa.data.mapper.GifMapper
import com.lufthansa.data.remote.GiphyContract
import com.lufthansa.domain.model.Gif
import com.lufthansa.domain.repository.GiphyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Named

class GiphyRepositoryImpl @Inject constructor(
    private val api: GiphyContract,
    @Named("GiphyApiKey") private val apiKey: String
) : GiphyRepository {

    override suspend fun getTrending(limit: Int, offset: Int): Result<List<Gif>> = withContext(Dispatchers.IO) {
        runCatching {
            val resp = api.trending(apiKey, limit, offset)
            GifMapper.fromDtoList(resp.data)
        }
    }

    override suspend fun search(query: String, limit: Int, offset: Int): Result<List<Gif>> = withContext(Dispatchers.IO) {
        runCatching {
            val resp = api.search(apiKey, query, limit, offset)
            GifMapper.fromDtoList(resp.data)
        }
    }
}