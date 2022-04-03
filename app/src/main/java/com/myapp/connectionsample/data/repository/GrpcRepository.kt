package com.myapp.connectionsample.data.repository

import com.myapp.connectionsample.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface GrpcRepository {

    suspend fun unaryApi() : Flow<ApiResult<String>>
}