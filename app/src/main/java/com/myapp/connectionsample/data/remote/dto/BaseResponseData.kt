package com.myapp.connectionsample.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * サンプルレスポンスデータ
 *
 * @property message メッセージ
 */
@Serializable
data class BaseResponseData(
    val message: String
)