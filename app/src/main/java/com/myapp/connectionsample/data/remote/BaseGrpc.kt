package com.myapp.connectionsample.data.remote

import com.myapp.connectionsample.model.ApiResult
import com.myapp.connectionsample.model.AppError
import io.grpc.Status
import io.grpc.StatusException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

abstract class BaseGrpc {

    /**
     * APIの呼び出し結果をFlow型で返却
     *
     * @param T レスポンス型
     * @param grpcApi 呼び出すAPI
     * @return Flow(呼び出し結果)
     */
    protected fun < T : Any> call( grpcApi: suspend () -> T): Flow<ApiResult<T>> = flow {
        runCatching { grpcApi() }
            .onSuccess { emit(ApiResult.Success(value = it)) }
            .onFailure { emit(ApiResult.Error(it.toApiError())) }
    }.flowOn(Dispatchers.IO)

    /**
     * ExceptionをAPI用エラーへ変換
     * ※ Presentation層での制御のため
     *
     * @return
     */
    private fun Throwable.toApiError(): AppError {
        return when (this) {
            is AppError -> this
            is StatusException -> {
                when (this.status.code) {
                    Status.UNAVAILABLE.code -> {
                        AppError.ApiException.NetworkException(this)
                    }
                    else -> {
                        AppError.UnknownException(this)
                    }
                }
            }
            else -> AppError.UnknownException(this)
        }
    }
}