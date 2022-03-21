package com.myapp.connectionsample.ui.util

import com.myapp.connectionsample.model.AppError

/**
 * エラー種別からエラーメッセージ取得
 *
 * @return
 */
fun AppError.getErrorMessage(): String {
    return when(this) {
        is AppError.ApiException.NetworkException -> "ネットワークに接続してください"
        is AppError.ApiException.ServerException -> "サーバーで予期せぬエラーが発生しました"
        is AppError.ApiException.TimeoutException -> "電波が悪いため通信環境の良いところへ移動してください"
        is AppError.ApiException.SessionNotFoundException -> "ログアウトされました。ログインしてください"
        else ->"予期せぬエラーが発生しました。"
    }
}