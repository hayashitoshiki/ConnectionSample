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
import androidx.hilt.navigation.compose.hiltViewModel
import com.myapp.connectionsample.ui.contract.SocketContract
import com.myapp.connectionsample.ui.viemodel.SocketViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach

@Composable
fun SocketScreen() {
    val context = LocalContext.current
    val viewModel: SocketViewModel = hiltViewModel()
    val event: (SocketContract.Event) -> Unit = { viewModel.setEvent(it) }
    LaunchedEffect(true) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is SocketContract.Effect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }.collect()
    }

    SocketContent(event)
}


@Composable
fun SocketContent(event: (SocketContract.Event) -> Unit) {
    var count = 1
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "Socket通信 Sample")
        Button(onClick = { event(SocketContract.Event.Connect) }) {
            Text(text = "接続")
        }
        Button(onClick = { event(SocketContract.Event.Cancel) }) {
            Text(text = "切断")
        }
        Button(
            onClick = {
                event(SocketContract.Event.SendMessage("" + count + "回目送信完了"))
                count++
            }
        ) {
            Text(text = "メッセージ送信")
        }

    }
}
