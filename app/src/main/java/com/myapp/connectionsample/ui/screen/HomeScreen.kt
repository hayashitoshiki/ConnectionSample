package com.myapp.connectionsample.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.myapp.connectionsample.ui.Screens

@Composable
fun HomeScreen(navController: NavHostController) {
    val httpNavigate = { navController.navigate(Screens.Http.route) }
    val socketNavigate = { navController.navigate(Screens.Socket.route) }
    val grpcNavigate = { navController.navigate(Screens.Grpc.route) }
    HomeContent(httpNavigate, socketNavigate, grpcNavigate)
}

@Composable
private fun HomeContent(
    httpNavigate: () ->Unit,
    socketNavigate: () ->Unit,
    grpcNavigate: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Connection Sample")

        Button(onClick = { httpNavigate() }) {
            Text(text = "HTTP Sample")
        }
        Button(onClick = { socketNavigate() }) {
            Text(text = "Socket Sample")
        }
        Button(onClick = { grpcNavigate() }) {
            Text(text = "gRPC Sample")
        }
    }
}