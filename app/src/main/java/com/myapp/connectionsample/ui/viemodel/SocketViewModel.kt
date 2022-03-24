package com.myapp.connectionsample.ui.viemodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.myapp.connectionsample.data.repository.SocketRepository
import com.myapp.connectionsample.model.PeopleState
import com.myapp.connectionsample.ui.contract.SocketContract
import com.myapp.connectionsample.ui.util.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SocketViewModel @Inject constructor(private val socketRepository: SocketRepository)
    : BaseViewModel<SocketContract.State, SocketContract.Effect, SocketContract.Event>() {

    init {
        socketRepository.receiveMessageFlow
            .onEach {
                Log.d("TAG", "message : $it")
                setEffect { SocketContract.Effect.ShowToast("メッセージ受信：$it") }
            }.launchIn(viewModelScope)
        socketRepository.receiveUserFlow
            .onEach {
                Log.d("TAG", "user : $it")
                val message = when(it.state) {
                    PeopleState.IN ->{
                        "ユーザが入室しました： user = " + it.user
                    }
                    PeopleState.OUT ->{
                        "メッセージ退出しました： user = " + it.user
                    }
                }
                setEffect { SocketContract.Effect.ShowToast(message)
                }
            }.launchIn(viewModelScope)
    }

    override fun initState(): SocketContract.State {
        return SocketContract.State()
    }

    override fun handleEvents(event: SocketContract.Event) {
        when (event) {
            is SocketContract.Event.Cancel -> socketCancel()
            is SocketContract.Event.Connect -> socketConnect()
            is SocketContract.Event.SendMessage -> sendMessage(event.message)
        }
    }

    private fun socketCancel() {
        socketRepository.close()
    }

    private fun sendMessage(message: String) {
        socketRepository.sendMessage(message)
    }

    private fun socketConnect() = viewModelScope.launch {
        socketRepository.create()
    }

}