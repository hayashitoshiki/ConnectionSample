package com.myapp.connectionsample.ui.screen

import android.widget.Toast
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
import com.myapp.connectionsample.ui.viemodel.HttpViewModel
import androidx.hilt.navigation.compose.hiltViewModel
import com.myapp.connectionsample.ui.contract.HttpContract
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun HttpScreen() {
    val context = LocalContext.current
    val viewModel: HttpViewModel = hiltViewModel()
    val event: (HttpContract.Event) -> Unit = { viewModel.setEvent(it) }
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is HttpContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.collect()
    }

    HttpContent(event)
}


@Composable
fun HttpContent(event: (HttpContract.Event) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Http通信 Sample")
        Button(onClick = { event(HttpContract.Event.CallGetApi) }) {
            Text(text = "Getリクエスト")
        }
    }
}
