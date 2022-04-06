package com.myapp.connectionsample.data.repository

import com.myapp.connectionsample.Sample
import com.myapp.connectionsample.data.remote.GrpcService
import com.myapp.connectionsample.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface GrpcRepository {

    suspend fun callUnaryApi() : Flow<ApiResult<String>>

    suspend fun callClientStreamingApi(sheepList: List<String>)

    fun callServerStreamingApi(userName: String) : Flow<Sample.SheepOutResponse>

    fun callBidirectionalStreamingApi(stream: Flow<String>) : Flow<Sample.ChatResponse>
}