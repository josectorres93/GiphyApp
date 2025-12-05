package com.lufthansa.giphyapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.lufthansa.giphyapp.ui.theme.GiphyTestTheme
import com.lufthansa.presentation.nav.AppNavigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GiphyTestTheme {
                AppNavigation()
            }
        }
    }
}