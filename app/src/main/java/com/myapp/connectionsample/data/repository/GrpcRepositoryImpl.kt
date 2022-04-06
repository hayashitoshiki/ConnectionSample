package com.myapp.connectionsample.data.repository

import com.myapp.connectionsample.Sample
import com.myapp.connectionsample.data.remote.BaseGrpc
import com.myapp.connectionsample.data.remote.GrpcService
import com.myapp.connectionsample.model.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GrpcRepositoryImpl @Inject constructor() : GrpcRepository, BaseGrpc() {

    override suspend fun callUnaryApi() : Flow<ApiResult<String>> {
        return call { GrpcService.unaryApi() }
    }

    override suspend fun callClientStreamingApi(sheepList: List<String>) {
        GrpcService.clientStreamingApi(sheepList)
    }

    override fun callServerStreamingApi(userName: String) : Flow<Sample.SheepOutResponse> {
        return GrpcService.serverStreamingApi(userName)
    }

    override fun callBidirectionalStreamingApi(stream: Flow<String>) : Flow<Sample.ChatResponse> {
        return GrpcService.bidirectionalStreamingApi(stream)
    }

}