package com.myapp.connectionsample.data.remote.service

import com.myapp.connectionsample.data.remote.dto.BaseResponseData
import retrofit2.Response
import retrofit2.http.GET

/**
 * HTTPサーバ URL管理
 *
 */
interface HttpService {
    @GET("/sample1")
    suspend fun getRawResponseForGet(): Response<BaseResponseData>

}