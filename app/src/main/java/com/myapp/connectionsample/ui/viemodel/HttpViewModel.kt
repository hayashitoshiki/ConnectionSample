package com.myapp.connectionsample.ui.viemodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.myapp.connectionsample.data.repository.HttpRepository
import com.myapp.connectionsample.model.ApiResult
import com.myapp.connectionsample.ui.contract.HttpContract
import com.myapp.connectionsample.ui.util.BaseViewModel
import com.myapp.connectionsample.ui.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HttpViewModel @Inject constructor(private val httpRepository: HttpRepository)
    : BaseViewModel<HttpContract.State, HttpContract.Effect, HttpContract.Event>() {

    override fun initState(): HttpContract.State {
        return HttpContract.State()
    }

    override fun handleEvents(event: HttpContract.Event)  {
        callGetApi()
    }

    private fun callGetApi() = viewModelScope.launch {
        httpRepository.callGetApi().onEach {
            when(it) {
                is ApiResult.Success -> {
                    Log.d("TAG", "取得成功")
                    setEffect { HttpContract.Effect.ShowToast("取得成功。" + it.value.message) }
                }
                is ApiResult.Error -> {
                    Log.d("TAG", "取得失敗 error = " + it.error)
                    setEffect { HttpContract.Effect.ShowToast( it.error.getErrorMessage()) }
                }
            }
        }.collect()
    }

}