package com.myapp.connectionsample.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.myapp.connectionsample.ui.screen.HomeScreen
import com.myapp.connectionsample.ui.screen.HttpScreen
import com.myapp.connectionsample.ui.screen.SocketScreen

/**
 * 画面定義
 *
 * @property title
 * @property route
 */
sealed class Screens(val title: String, val route: String) {
    object Home : Screens("Home", "home")
    object Http : Screens("Http", "http")
    object Socket : Screens("Socket", "socket")
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
        composable(Screens.Http.route) {
            HttpScreen()
        }
        composable(Screens.Socket.route) {
            SocketScreen()
        }
    }
}