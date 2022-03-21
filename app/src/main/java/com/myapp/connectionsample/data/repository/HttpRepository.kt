package com.myapp.connectionsample.data.repository

import com.myapp.connectionsample.data.remote.dto.BaseResponseData
import com.myapp.connectionsample.model.ApiResult
import kotlinx.coroutines.flow.Flow

interface HttpRepository {

    suspend fun callGetApi(): Flow<ApiResult<BaseResponseData>>
}