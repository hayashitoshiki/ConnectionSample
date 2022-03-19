package com.myapp.connectionsample.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myapp.connectionsample.ui.screen.HomeScreen

/**
 * 画面定義
 *
 * @property title
 * @property route
 */
sealed class Screens(val title: String, val route: String) {
    object Home : Screens("Home", "home")
}

/**
 * 画面遷移定義
 *
 * @param navController
 */
@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route
    ) {
        composable(Screens.Home.route) {
            HomeScreen(navController)
        }
    }
}