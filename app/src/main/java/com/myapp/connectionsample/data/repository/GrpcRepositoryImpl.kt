package com.myapp.connectionsample.data.repository

import com.myapp.connectionsample.data.remote.BaseGrpc
import com.myapp.connectionsample.data.remote.GrpcService
import com.myapp.connectionsample.model.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GrpcRepositoryImpl @Inject constructor() : GrpcRepository, BaseGrpc() {

    override suspend fun unaryApi() : Flow<ApiResult<String>> {
        return call { GrpcService.unaryApi() }
    }

}