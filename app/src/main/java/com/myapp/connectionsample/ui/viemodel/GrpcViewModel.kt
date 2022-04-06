package com.myapp.connectionsample.ui.viemodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.myapp.connectionsample.data.repository.GrpcRepository
import com.myapp.connectionsample.model.ApiResult
import com.myapp.connectionsample.ui.contract.GrpcContract
import com.myapp.connectionsample.ui.util.BaseViewModel
import com.myapp.connectionsample.ui.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrpcViewModel @Inject constructor(private val grpcRepository: GrpcRepository)
    : BaseViewModel<GrpcContract.State, GrpcContract.Effect, GrpcContract.Event>() {

    private val _chatStream: Channel<String> = Channel()
    private val chatStream: Flow<String> = _chatStream.receiveAsFlow()

    override fun initState(): GrpcContract.State {

        grpcRepository.callServerStreamingApi("userA").onEach {
            Log.d("TAG", "羊：" + it.sheepName)
        }.launchIn(viewModelScope)

        val sheepInList = mutableListOf<String>()
        for(i in 1..100) {
            sheepInList.add("羊No.$i")
        }
        viewModelScope.launch {
            grpcRepository.callClientStreamingApi(sheepInList)
        }

        grpcRepository.callBidirectionalStreamingApi(chatStream).onEach {
            Log.d("TAG", "取得成功。入力内容：" + it.message)
        }.launchIn(viewModelScope)
        return GrpcContract.State()
    }

    override fun handleEvents(event: GrpcContract.Event) {
        when(event) {
            GrpcContract.Event.CallUnaryApi -> {
                callUnaryApi()
            }
            is GrpcContract.Event.SendMessage -> {
                sendMessage(event.message)
            }
            is GrpcContract.Event.SendSheep -> {
              //  sendSheep(event.name)
            }
        }
    }

    private fun callUnaryApi() = viewModelScope.launch {
        grpcRepository.callUnaryApi().onEach {
            when(it) {
                is ApiResult.Success -> {
                    Log.d("TAG", "取得成功。計算結果：" + it.value)
                    setEffect { GrpcContract.Effect.ShowToast("取得成功。計算結果：" + it.value) }
                }
                is ApiResult.Error -> {
                    Log.d("TAG", "取得失敗 error = " + it.error)
                    setEffect { GrpcContract.Effect.ShowToast( it.error.getErrorMessage()) }
                }
            }
        }.collect()
    }


    private fun sendMessage(message: String) = viewModelScope.launch {
        _chatStream.send(message)
    }

}