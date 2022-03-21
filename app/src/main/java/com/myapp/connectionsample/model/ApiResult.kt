package com.myapp.connectionsample.model

/**
 * API呼び出し結果管理
 *
 * @param T
 */
sealed class ApiResult<out T>{
    // APIコールが成功した
    data class Success<out T>(val value: T) : ApiResult<T>()

    // APIコールが失敗した
    data class Error(val error: AppError) : ApiResult<Nothing>()
}
