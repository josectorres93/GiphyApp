package com.lufthansa.presentation.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GifDetailScreen(
    id: String,
    title: String,
    gifUrl: String,
    onBack: () -> Unit
) {
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(title.ifBlank { "GIF Detail" }) }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            if (gifUrl.isNotBlank()) {
                GifItem(url = gifUrl, title = title, context = context)
            } else {
                Text("No image available", modifier = Modifier.padding(16.dp))
            }

            Spacer(Modifier.height(12.dp))
            Text("ID: $id", modifier = Modifier.padding(16.dp))
            Text("Title: $title", modifier = Modifier.padding(16.dp))

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text("Back")
            }
        }
    }
}



