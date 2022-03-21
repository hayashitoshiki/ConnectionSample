package com.myapp.connectionsample.data.repository

import com.myapp.connectionsample.data.remote.dto.BaseResponseData
import com.myapp.connectionsample.data.remote.service.BaseApi
import com.myapp.connectionsample.data.remote.service.Provider
import com.myapp.connectionsample.model.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class HttpRepositoryImpl @Inject constructor(): HttpRepository, BaseApi() {

    override suspend fun callGetApi(): Flow<ApiResult<BaseResponseData>> {
        return call{ Provider.httpSampleApi().getRawResponseForGet() }
    }
}