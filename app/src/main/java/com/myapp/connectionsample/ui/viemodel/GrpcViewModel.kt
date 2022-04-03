package com.myapp.connectionsample.ui.viemodel

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.myapp.connectionsample.data.repository.GrpcRepository
import com.myapp.connectionsample.data.repository.HttpRepository
import com.myapp.connectionsample.model.ApiResult
import com.myapp.connectionsample.ui.contract.GrpcContract
import com.myapp.connectionsample.ui.contract.HttpContract
import com.myapp.connectionsample.ui.util.BaseViewModel
import com.myapp.connectionsample.ui.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GrpcViewModel @Inject constructor(private val grpcRepository: GrpcRepository)
    : BaseViewModel<GrpcContract.State, GrpcContract.Effect, GrpcContract.Event>() {

    override fun initState(): GrpcContract.State {
        return GrpcContract.State()
    }

    override fun handleEvents(event: GrpcContract.Event)  {
        callGetApi()
    }

    private fun callGetApi() = viewModelScope.launch {
        grpcRepository.unaryApi().onEach {
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

}