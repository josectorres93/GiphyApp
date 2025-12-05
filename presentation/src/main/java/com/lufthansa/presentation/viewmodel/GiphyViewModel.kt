package com.lufthansa.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lufthansa.domain.model.Gif
import com.lufthansa.domain.usecase.GetTrendingUseCase
import com.lufthansa.domain.usecase.SearchGifsUseCase
import com.lufthansa.presentation.ui.UiState

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GiphyViewModel @Inject constructor(
    private val getTrending: GetTrendingUseCase,
    private val searchGifs: SearchGifsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UiState<List<Gif>>>(UiState.Loading)
    val uiState: StateFlow<UiState<List<Gif>>> = _uiState

    private val _query = MutableStateFlow("")
    val query: StateFlow<String> = _query.asStateFlow()

    init {
        observeSearch()
        loadTrending()
    }

    fun onQueryChanged(q: String) {
        _query.value = q
    }

    suspend fun retry() {
        val current = query.value
        if (current.isBlank()) loadTrending()
        else search(current)
    }

    @OptIn(FlowPreview::class)
    private fun observeSearch() {
        viewModelScope.launch {
            _query
                .debounce(350)
                .distinctUntilChanged()
                .drop(1)
                .collectLatest { q ->
                    if (q.isBlank()) loadTrending()
                    else search(q)
                }
        }
    }

    private suspend fun search(q: String) {
        _uiState.value = UiState.Loading
        val result = searchGifs(q)

        _uiState.value = result.fold(
            onSuccess = { UiState.Success(it) },
            onFailure = { UiState.Error(it.message ?: "Search failed") }
        )
    }

    private fun loadTrending() {
        viewModelScope.launch {
            _uiState.value = UiState.Loading

            val result = getTrending()

            _uiState.value = result.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = { UiState.Error(it.message ?: "Could not load trending GIFs") }
            )
        }
    }
}
