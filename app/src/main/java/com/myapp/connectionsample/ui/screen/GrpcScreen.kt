package com.myapp.connectionsample.ui.screen

import android.util.Log
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.myapp.connectionsample.ui.contract.GrpcContract
import com.myapp.connectionsample.ui.viemodel.GrpcViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach


@Composable
fun GrpcScreen() {
    val context = LocalContext.current
    val viewModel: GrpcViewModel = hiltViewModel()
    val event: (GrpcContract.Event) -> Unit = { viewModel.setEvent(it) }
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is GrpcContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.collect()
    }

    GrpcContent(event)
}


@Composable
fun GrpcContent(event: (GrpcContract.Event) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "gRPC通信 Sample")
        Button(onClick = { event(GrpcContract.Event.CallUnaryApi) }) {
            Text(text = "単一送信")
        }
    }
}
