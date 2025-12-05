package com.lufthansa.presentation.nav

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.lufthansa.presentation.ui.GifDetailScreen
import com.lufthansa.presentation.ui.GifListScreen
import com.lufthansa.presentation.viewmodel.GiphyViewModel
import java.net.URLDecoder
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            val vm = hiltViewModel<GiphyViewModel>()
            GifListScreen(viewModel = vm, onGifClick = { gif ->
                val titleEnc = URLEncoder.encode(gif.title, StandardCharsets.UTF_8.toString())
                val urlEnc = URLEncoder.encode(gif.fullUrl ?: "", StandardCharsets.UTF_8.toString())
                navController.navigate("detail/${gif.id}/$titleEnc/$urlEnc")
            })
        }

        composable(
            route = "detail/{id}/{title}/{url}",
            arguments = listOf(
                navArgument("id") { type = NavType.StringType },
                navArgument("title") { type = NavType.StringType },
                navArgument("url") { type = NavType.StringType }
            )
        ) { backStack ->
            val id = backStack.arguments?.getString("id") ?: ""
            val title = backStack.arguments?.getString("title")?.let { URLDecoder.decode(it, StandardCharsets.UTF_8.toString()) } ?: ""
            val url = backStack.arguments?.getString("url")?.let { URLDecoder.decode(it, StandardCharsets.UTF_8.toString()) } ?: ""
            GifDetailScreen(id = id, title = title, gifUrl = url, onBack = { navController.popBackStack() })
        }
    }
}