package com.lufthansa.domain.usecase

import com.lufthansa.domain.repository.GiphyRepository
import javax.inject.Inject

class GetTrendingUseCase @Inject constructor(
    private val repository: GiphyRepository
) {
    suspend operator fun invoke(limit: Int = 25, offset: Int = 0) = repository.getTrending(limit, offset)
}