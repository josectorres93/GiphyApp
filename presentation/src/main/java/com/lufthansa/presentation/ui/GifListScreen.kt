package com.lufthansa.presentation.ui

import android.content.Context
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.request.ImageRequest
import coil.compose.AsyncImage
import com.lufthansa.domain.model.Gif
import com.lufthansa.presentation.viewmodel.GiphyViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifListScreen(viewModel: GiphyViewModel, onGifClick: (Gif) -> Unit) {
    val state = viewModel.uiState.collectAsState().value
    val query = viewModel.query.collectAsState().value
    val context = LocalContext.current
    val scope = rememberCoroutineScope()


    Scaffold(
        topBar = { TopAppBar(title = { Text("GIPHY Search") }) }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = query,
                onValueChange = { viewModel.onQueryChanged(it) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                placeholder = { Text("Search GIFs") },
                singleLine = true
            )

            when (state) {
                is UiState.Loading -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) { CircularProgressIndicator() }

                is UiState.Error -> Box(
                    Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("Error: ${state.message}")
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = {
                            scope.launch {
                                viewModel.retry()
                            }
                        }) {
                            Text("Retry")
                        }
                    }
                }

                is UiState.Success<List<Gif>> -> {
                    val gifs = state.data
                    if (gifs.isEmpty()) {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Text("No results")
                        }
                    } else {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(8.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(gifs) { gif ->
                                Card(
                                    modifier = Modifier
                                        .height(180.dp)
                                        .fillMaxWidth()
                                        .clickable { onGifClick(gif) }
                                ) {
                                    val url = gif.previewUrl ?: gif.fullUrl
                                    if (!url.isNullOrBlank()) {
                                        GifItem(url, gif.title, context)
                                    } else {
                                        Box(contentAlignment = Alignment.Center) {
                                            Text(gif.title.ifBlank { "GIF" })
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun GifItem(url: String, title: String, context: Context) {
    AsyncImage(
        model = ImageRequest.Builder(context)
            .data(url)
            .build(),
        contentDescription = title,
        modifier = Modifier.fillMaxSize(),
        imageLoader = context.gifImageLoader()
    )
}

