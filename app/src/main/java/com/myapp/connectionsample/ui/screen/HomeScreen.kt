package com.myapp.connectionsample.ui.screen

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import com.myapp.connectionsample.ui.Screens
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun HomeScreen(navController: NavHostController) {
    val httpNavigate = { navController.navigate(Screens.Http.route) }
    HomeContent(httpNavigate)
}

@Composable
private fun HomeContent(httpNavigate: () ->Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Connection Sample")

        Button(onClick = { httpNavigate() }) {
            Text(text = "HTTP Sample")
        }
    }
}