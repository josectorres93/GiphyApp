package com.lufthansa.domain.usecase

import com.lufthansa.domain.repository.GiphyRepository
import javax.inject.Inject

class SearchGifsUseCase @Inject constructor(
    private val repository: GiphyRepository
) {
    suspend operator fun invoke(query: String, limit: Int = 25, offset: Int = 0) = repository.search(query, limit, offset)
}