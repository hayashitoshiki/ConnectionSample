package com.myapp.connectionsample.data.remote.service

import com.myapp.connectionsample.model.ApiResult
import com.myapp.connectionsample.model.AppError
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException

/**
 * API呼び出しに関するBaseクラス
 *
 * RepositoryからAPIを呼び出す際はこのクラスを継承して、
 * call()メソッドを呼び出してその結果を返却
 *
 */
abstract class BaseApi {

    /**
     * APIの呼び出し結果をFlow型で返却
     *
     * @param T レスポンス型
     * @param api 呼び出すAPI
     * @return Flow(呼び出し結果)
     */
    protected fun < T : Any> call( api: suspend () -> Response<T>): Flow<ApiResult<T>> = flow {
        val response = api()
        if (response.isSuccessful) {
            emit(ApiResult.Success(value = response.body()!!))
        } else {
            emit(ApiResult.Error(response.code().toApiError()))
        }
    }.catch {
        emit(ApiResult.Error(it.toAppError()))
    }.flowOn(Dispatchers.IO)

    /**
     * ステータスコードからApiExceptionへ変換
     *
     * @return ApiException
     */
    private fun Int.toApiError(): AppError {
        return when(this) {
            401 ->{
                AppError.ApiException.SessionNotFoundException(null)
            }
            500 -> {
                AppError.UnknownException(null)
            }
            else -> throw IllegalAccessException()
        }
    }

    /**
     * ExceptionをAPI用エラーへ変換
     * ※ Presentation層での制御のため
     *
     * @return
     */
    private fun Throwable.toAppError(): AppError {
        return when (this) {
            is AppError -> this
            is TimeoutCancellationException, is SocketTimeoutException -> {
                AppError.ApiException.TimeoutException(this)
            }
            is ConnectException -> {
                return AppError.ApiException.ServerException(this)
            }
            else -> AppError.UnknownException(this)
        }
    }
}
